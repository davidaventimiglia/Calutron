package org.neptunestation.calutron;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.bind.*;
import org.apache.olingo.odata2.api.commons.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.ep.*;
import org.apache.olingo.odata2.api.exception.*;
import org.neptunestation.calutron.commands.*;
import org.neptunestation.calutron.model.*;

public class Calutron implements CalutronModel {
    private static final String HTTP_METHOD_PUT = "PUT";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_ATOM_XML = "application/atom+xml";
    private static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    private static final String METADATA = "$metadata";
    private static final boolean PRINT_RAW_CONTENT = false;
    private static final String CONTENT_TYPE = APPLICATION_XML;

    // State

    protected final Properties settings = new Properties();
    protected Edm edm = null;

    // Main Loop

    public static void main (String[] args) throws IOException, ODataException {
        Calutron calutron = new Calutron();
        Interpreter interpreter = new Interpreter(new AbstractCommandContext(null, calutron, new Properties()){}, "interpreter");
        interpreter.addCommands(new GetEdm(interpreter.getContext(), "rehash"),
                                new Connect(interpreter.getContext(), "connect"),
                                new Quit(interpreter.getContext(), "quit"),
                                new Help(interpreter.getContext(), "help"),
                                new SetPassword(interpreter.getContext(), "set password"),
                                new SetUrl(interpreter.getContext(), "set url"),
                                new ShowEntitySets(interpreter.getContext(), "show entity sets"),
                                new SetUsername(interpreter.getContext(), "set username"));
        try {interpreter.execute();}
        catch (StoppedException e) {System.exit(0);}
        catch (Throwable t) {t.printStackTrace(System.err); System.exit(1);}}

    // Model API

    @Override public Edm readEdm (final String serviceUrl, final String username, final String password) throws IOException, ODataException {
        return EntityProvider.readMetadata(call(serviceUrl + "/" + METADATA, CONTENT_TYPE, HTTP_METHOD_GET, username, password), false);}

    @Override public void setEdm (final Edm edm) {
        this.edm = edm;}

    @Override public Edm getEdm () {
        return this.edm;}

    // ODATA Communication methods

    protected InputStream call (final String relativeUri, final String contentType, final String httpMethod, final String username, final String password) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod, username, password);
        connection.connect();
        checkStatus(connection);
        InputStream content = connection.getInputStream();
        return content;}

    protected HttpURLConnection connect (final String relativeUri, final String contentType, final String httpMethod, final String username, final String password) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod, username, password);
        connection.connect();
        checkStatus(connection);
        return connection;}

    protected HttpURLConnection initializeConnection (final String absoluteUri, final String contentType, final String httpMethod, final String username, final String password) throws MalformedURLException, IOException {
        URL url = new URL(absoluteUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        String creds = String.format("%s:%s", username, password);
        String base64 = DatatypeConverter.printBase64Binary(creds.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + base64);
        if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);}
        return connection;}

    protected HttpStatusCodes checkStatus (final HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        return httpStatusCode;}}

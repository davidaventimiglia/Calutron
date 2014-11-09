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

public class Calutron implements Interpreter {
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

    // Main Loop

    public static void main (String[] args) throws IOException, ODataException {
        Console console = System.console();
        if (console==null) System.exit(1);
        Calutron calutron = new Calutron(console);
        calutron.addCommands(new Quit(calutron, "quit"),
                             new Help(calutron, "help"),
                             new SetPassword(calutron, "set password"),
                             new SetUrl(calutron, "set url"),
                             new ShowEntitySets(calutron, "show entity sets"),
                             new SetUsername(calutron, "set username"));
        try {calutron.start();}
        catch (StoppedException e) {System.exit(0);}
        catch (Throwable t) {t.printStackTrace(System.err); System.exit(1);}}

    // State

    protected final CommandMap commands = new CommandMap();
    protected final Properties settings = new Properties();
    protected Console console = null;

    // Constructors

    public Calutron (final Console console) {
        this.console = console;}

    public Calutron (final Console console, final Properties settings) {
        this(console);
        this.settings.putAll(settings);}

   // API

    @Override public CommandMap getCommands () {
        return commands;}

    @Override public Properties getSettings () {
        return settings;}

    @Override public String getSetting (final String name) {
        return getSettings().getProperty(name);}

    @Override public void setSetting (final String name, final String value) {
        if (name==null) throw new NullArgumentException("name");
        if (value==null) throw new NullArgumentException("value");
        getSettings().setProperty(name, value);}

    @Override public Console getConsole () {
        return console;}

    @Override public String getPrompt () {
        return String.format("%s:%s@%s> ",
                             getSetting("USERNAME")==null ? "[username]" : getSetting("USERNAME"),
                             getSetting("PASSWORD")==null ? "[password]" : "****",
                             getSetting("SERVICE_URL")==null ? "[url]" : getEndPointName(getSetting("SERVICE_URL"))).toUpperCase();}

    protected String getEndPointName (String url) {
        URL u = null;
        try {u = new URL(url);} catch (MalformedURLException e) {return "[BAD URL]";}
        if (u.getPort()==80) return String.format("%s/%s", u.getHost(), u.getPath());
        return String.format("%s:%s/%s", u.getHost(), u.getPort(), u.getPath());}

    @Override public void executeCommand (final String name) {
        if (name==null) throw new NullArgumentException("name");
        getCommand(name).execute();}
    
    @Override public void addCommands (final Command... commands) {
        this.commands.add(commands);}

    @Override public Command getCommand (final String name) {
        if (name==null) throw new NullArgumentException("name");
        if (getCommands().containsKey(name.toUpperCase())) return getCommands().get(name.toUpperCase());
        return new AbstractCommand(this, "default") {@Override public void execute () {getConsole().printf("%s\n", "Command not found");}};}

    @Override public void start () {
        while (true) getCommand(getConsole().readLine(getPrompt()).replaceAll("\\s+", " ").trim()).execute();}


    // Helper methods

    @Override public Edm readEdm (String serviceUrl, String username, String password) throws IOException, ODataException {
        return EntityProvider.readMetadata(execute(serviceUrl + "/" + METADATA, CONTENT_TYPE, HTTP_METHOD_GET, username, password), false);}

    protected InputStream execute (String relativeUri, String contentType, String httpMethod, String username, String password) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod, username, password);
        connection.connect();
        checkStatus(connection);
        InputStream content = connection.getInputStream();
        return content;}

    protected HttpURLConnection connect (String relativeUri, String contentType, String httpMethod, String username, String password) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod, username, password);
        connection.connect();
        checkStatus(connection);
        return connection;}

    protected HttpURLConnection initializeConnection (String absoluteUri, String contentType, String httpMethod, String username, String password) throws MalformedURLException, IOException {
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

    protected HttpStatusCodes checkStatus (HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        return httpStatusCode;}}

import java.io.*;
import java.net.*;
import javax.xml.bind.*;
import org.apache.olingo.odata2.api.commons.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.ep.*;
import org.apache.olingo.odata2.api.exception.*;

public class Calutron {
    private static final String HTTP_METHOD_PUT = "PUT";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_ATOM_XML = "application/atom+xml";
    private static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    private static final String METADATA = "$metadata";
    private static final String INDEX = "/index.jsp";
    private static final String SEPARATOR = "/";
    private static final boolean PRINT_RAW_CONTENT = true;

    // State

    private static String SERVICE_URL = null;
    private static String CONTENT_TYPE = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;

    // Commands

    private static void setServiceUrl (Console console) {SERVICE_URL = console.readLine("Service URL: ");}
    private static void setUserName (Console console) {USERNAME = console.readLine("Username: ");}
    private static void setPassword (Console console) {PASSWORD = console.readLine("Password: ");}
    private static void showMetaData (Console console) throws IOException, ODataException {System.out.println(readEdm(SERVICE_URL));}
    private static void setContentTypeXML () {CONTENT_TYPE = APPLICATION_XML;}
    private static void setContentTypeJSON () {CONTENT_TYPE = APPLICATION_JSON;}
    private static void showServiceDoc (Console console) throws IOException, ODataException {}
    private static void quit () {System.exit(0);}

    // Main Loop

    public static void main (String[] args) throws IOException, ODataException {
        Console console = System.console();
        if (console==null) System.exit(1);
        setContentTypeXML();
        while (true) {
            String input = console.readLine("CALUTRON>").replaceAll("\\s+", " ").trim();
            if (input.equalsIgnoreCase("set service url")) setServiceUrl(console);
            if (input.equalsIgnoreCase("show metadata")) showMetaData(console);
            if (input.equalsIgnoreCase("set username")) setUserName(console);
            if (input.equalsIgnoreCase("set password")) setPassword(console);
            if (input.equalsIgnoreCase("set out xml")) setContentTypeXML();
            if (input.equalsIgnoreCase("set out json")) setContentTypeJSON();
            if (input.equalsIgnoreCase("show service doc")) showServiceDoc(console);
            if (input.equalsIgnoreCase("quit")) quit();
            if (input.equalsIgnoreCase("exit")) quit();}}

    // Helper methods

    private static Edm readEdm (String serviceUrl) throws IOException, ODataException {
        return EntityProvider.readMetadata(execute(serviceUrl + "/" + METADATA, CONTENT_TYPE, HTTP_METHOD_GET), false);}

    private static InputStream execute (String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);
        connection.connect();
        checkStatus(connection);
        InputStream content = connection.getInputStream();
        content = logRawContent(httpMethod + " request:\n  ", content, "\n");
        return content;}

    private static HttpURLConnection connect (String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);
        connection.connect();
        checkStatus(connection);
        return connection;}

    private static HttpURLConnection initializeConnection (String absolutUri, String contentType, String httpMethod) throws MalformedURLException, IOException {
        URL url = new URL(absolutUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        String creds = String.format("%s:%s", USERNAME, PASSWORD);
        String base64 = DatatypeConverter.printBase64Binary(creds.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + base64);
        if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);}
        return connection;}

    private static HttpStatusCodes checkStatus (HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        return httpStatusCode;}

    private static InputStream logRawContent (String prefix, InputStream content, String postfix) throws IOException {
        if (PRINT_RAW_CONTENT) {
            byte[] buffer = streamToArray(content);
            content.close();
            System.out.println(prefix + new String(buffer) + postfix);
            return new ByteArrayInputStream(buffer);}
        return content;}

    private static byte[] streamToArray (InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] tmp = new byte[8192];
        int readCount = stream.read(tmp);
        while (readCount >= 0) {
            byte[] innerTmp = new byte[result.length + readCount];
            System.arraycopy(result, 0, innerTmp, 0, result.length);
            System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
            result = innerTmp;
            readCount = stream.read(tmp);}
        return result;}}

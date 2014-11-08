import java.io.*;
import java.net.*;
import java.util.*;
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

    private static interface Command {
        String getCommandString ();
        void setCommandString (String command);
        void execute ();}

    private static class NullArgumentException extends IllegalArgumentException {
        public NullArgumentException (Object param) {
            super(String.format("The `%s' parameter cannot be null.", param));}
        public NullArgumentException (Object param, Throwable cause) {
            super(String.format("The `%s' parameter cannot be null.", param), cause);}
        public NullArgumentException (Throwable cause) {
            super(cause);}}

    private static class StoppedException extends RuntimeException {
        public StoppedException (Throwable t) {
            super(t);}}

    private static abstract class AbstractCommand implements Command {
        String commandString = null;
        Calutron calutron = null;
        public AbstractCommand (final String command) {
            if (command==null) throw new NullArgumentException("command");
            setCommandString(command);}
        public AbstractCommand (final Calutron calutron, final String command) {
            if (calutron==null) throw new NullArgumentException("calutron");
            if (command==null) throw new NullArgumentException("command");
            this.calutron = calutron;
            setCommandString(command);}
        public void setCalutron (Calutron calutron) {
            if (calutron==null) throw new NullArgumentException("calutron");
            this.calutron = calutron;}
        public Calutron getCalutron () {
            return calutron;}
        public String getCommandString () {
            return commandString.toUpperCase();}
        public void setCommandString (String command) {
            if (command==null) throw new NullArgumentException("command");
            commandString = command.replaceAll("\\s+", " ").toUpperCase().trim();}}

    private static class CommandMap extends TreeMap<String, Command> {
        public void add (final Command command) {
            if (command==null) throw new NullArgumentException("command");
            super.put(command.getCommandString(), command);}
        public void add (final Command... commands) {
            if (commands==null) throw new NullArgumentException("commands");
            for (int i=0; i<commands.length; i++) add(commands[i]);}}

    protected final CommandMap commands = new CommandMap();
    protected Console console = null;
    protected final Properties settings = new Properties();

    public CommandMap getCommands () {return commands;}

    public Properties getSettings () {return settings;}

    public String getSetting (final String name) {
        return getSettings().getProperty(name);}

    public void setSetting (final String name, final String value) {
        if (name==null) throw new NullArgumentException("name");
        if (value==null) throw new NullArgumentException("value");
        getSettings().setProperty(name, value);}

    public Console getConsole () {return console;}

    public Calutron (final Console console) {
        this.console = console;}

    public Calutron (final Console console, final Properties settings) {
        this(console);
        this.settings.putAll(settings);}

    public void executeCommand (final String name) {
        if (name==null) throw new NullArgumentException("name");
        getCommand(name).execute();}
    
    public void addCommands (final Command... commands) {
        this.commands.add(commands);}

    public Command getCommand (final String name) {
        if (name==null) throw new NullArgumentException("name");
        if (getCommands().containsKey(name.toUpperCase())) return getCommands().get(name.toUpperCase());
        return new AbstractCommand(this, "default") {public void execute () {getConsole().printf("%s\n", "Command not found");}};}

    public void start () {
        while (true) try {getCommand(getConsole().readLine("CALUTRON>").replaceAll("\\s+", " ").trim()).execute();} catch (NullPointerException e) {throw new StoppedException(e);}}

    // Main Loop

    public static void main (String[] args) throws IOException, ODataException {
        Console console = System.console();
        if (console==null) System.exit(1);
        Calutron calutron = new Calutron(console);
        calutron.addCommands(new AbstractCommand(calutron, "quit") {public void execute () {System.exit(0);}},
                             new AbstractCommand(calutron, "help") {public void execute () {for (String s : getCalutron().getCommands().keySet()) getCalutron().getConsole().printf("%s\n", s);}},
                             new AbstractCommand(calutron, "show commands") {public void execute () {for (String s : getCalutron().getCommands().keySet()) getCalutron().getConsole().printf("%s\n", s);}},
                             new AbstractCommand(calutron, "set password") {public void execute () {getCalutron().setSetting("PASSWORD", new String(getCalutron().getConsole().readPassword("%s", "Password:")));}},
                             new AbstractCommand(calutron, "set service url") {public void execute () {getCalutron().setSetting("SERVICE_URL", getCalutron().getConsole().readLine("Service URL: "));}},
                             new AbstractCommand(calutron, "show service doc") {public void execute () {}},
                             new AbstractCommand(calutron, "show metadata") {public void execute () {try {getCalutron().getConsole().printf("%s\n", readEdm(getCalutron().getSettings().getProperty("SERVICE_URL")));} catch (Throwable t) {throw new RuntimeException(t);}}},
                             new AbstractCommand(calutron, "set username") {public void execute () {getCalutron().setSetting("USERNAME", getCalutron().getConsole().readLine("Username: "));}});
        try {calutron.start();}
        catch (StoppedException e) {System.exit(0);}
        catch (Throwable t) {System.exit(1);}}

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

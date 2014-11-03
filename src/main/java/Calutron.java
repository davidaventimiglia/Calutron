import java.io.*;
import java.net.*;
import javax.xml.bind.*;
import org.apache.olingo.odata2.api.commons.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.ep.*;
import org.apache.olingo.odata2.api.exception.*;

public class Calutron {
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_DELETE = "DELETE";

    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_HEADER_ACCEPT = "Accept";

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String METADATA = "$metadata";
    public static final String INDEX = "/index.jsp";
    public static final String SEPARATOR = "/";

    public static final boolean PRINT_RAW_CONTENT = true;

    public static void main (String[] args) throws IOException, ODataException {
        Console console = System.console();
        if (console==null) System.exit(1);
        String url = console.readLine("ODATA v2 service URL? ");
        Edm edm = readEdm(url);
        System.out.println(edm);}

    public static Edm readEdm(String serviceUrl) throws IOException, ODataException {
        InputStream content = execute(serviceUrl + "/" + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
        return EntityProvider.readMetadata(content, false);
    }

    public static InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        InputStream content = connection.getInputStream();
        content = logRawContent(httpMethod + " request:\n  ", content, "\n");
        return content;
    }

    public static HttpURLConnection connect(String relativeUri, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        return connection;
    }

    public static HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
        throws MalformedURLException, IOException {
        URL url = new URL(absolutUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        String creds = "test:test";
        String base64 = DatatypeConverter.printBase64Binary(creds.getBytes());
        connection.setRequestProperty("Authorization", base64);
        if(HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
        }

        return connection;
    }

    public static HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
            throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        }
        return httpStatusCode;
    }


    public static InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
        if(PRINT_RAW_CONTENT) {
            byte[] buffer = streamToArray(content);
            content.close();

            System.out.println(prefix + new String(buffer) + postfix);

            return new ByteArrayInputStream(buffer);
        }
        return content;
    }

    public static byte[] streamToArray(InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] tmp = new byte[8192];
        int readCount = stream.read(tmp);
        while(readCount >= 0) {
            byte[] innerTmp = new byte[result.length + readCount];
            System.arraycopy(result, 0, innerTmp, 0, result.length);
            System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
            result = innerTmp;
            readCount = stream.read(tmp);
        }
        return result;
    }
}

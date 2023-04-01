package lec_06_prg_01_http_web_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpCalcHandler implements HttpHandler {

    private final HttpPrintManager printManager;
    public HttpCalcHandler(PrintStream printStream) {
        printManager = new HttpPrintManager(printStream);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
        String method = exchange.getRequestMethod();

        String param;

        if(method.equals("GET")) {
            printManager.printGETDescription();
            param = getParametersFromURI(exchange.getRequestURI());
        } else if (method.equals("POST")) {
            printManager.printPOSTDescription();
            param = getParametersFromRequestBody(exchange.getRequestBody());
        } else {
            exchange.sendResponseHeaders(400, 0);
            return;
        }

        printManager.printHttpRequestDetail(exchange);

        String responseData;

        if (param != null) {
            responseData = makeCalculationResponse(param, method);
        } else {
            responseData = makeUnmappedPathResopnse(exchange.getRequestURI());
        }

        exchange.sendResponseHeaders(200, responseData.length());
        writer.write(responseData);
        writer.flush();

        writer.close();
    }

    private String makeCalculationResponse(String param, String method) {
        Map<String, Integer> paramData = parseFromParameters(param);

        int var1 = paramData.get("var1");
        int var2 = paramData.get("var2");
        int result = var1 * var2;

        String responseData = "<html>"
                + String.format("%s request for calculation => %d x %d = %d", method, var1, var2, result)
                + "</html>";

        if (method.equals("POST")) {
            printManager.printRequestData(method, param);
        }

        printManager.printCalcResponseData(method, var1, var2, result);

        return responseData;
    }

    private String makeUnmappedPathResopnse(URI uri) {
        String path = uri.getRawPath();

        String responseData = "<html>"
                + String.format("<p>HTTP Request GET for Path: %s</p>", path)
                + "</html>";

        printManager.printRequestPath(path);

        return responseData;
    }

    private String getParametersFromURI(URI uri) {
        return uri.getRawQuery();
    }

    private String getParametersFromRequestBody(InputStream requestStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestStream));

        StringBuilder sb = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();

        return sb.toString();
    }

    private Map<String, Integer> parseFromParameters(String param) {
        Map<String, Integer> parameters = new HashMap<>();

        Arrays.stream(param.split("&"))
                .forEach(field -> {
                    String[] keyAndValue = field.split("=");
                    parameters.put(keyAndValue[0], Integer.valueOf(keyAndValue[1]));
                });

        return parameters;
    }


}

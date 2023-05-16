package lec_06_prg_07_rest_server_v3;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.*;

public class HttpMembershipHandler implements com.sun.net.httpserver.HttpHandler {

    private final HttpPrintManager printManager;
    private final Gson gson = new Gson();
    private final Map<String, String> database = new HashMap<>();

    public HttpMembershipHandler(PrintStream printStream) {
        printManager = new HttpPrintManager(printStream);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String clientHost = exchange.getRemoteAddress().getHostString();
        String path = exchange.getRequestURI().getPath();
        String requestLine = String.format("%s %s %s", method, path, exchange.getProtocol());

        String id = getIdFromPath(path);
        JsonElement reqBody = parseParametersToJson(exchange.getRequestBody());
        JsonElement resBody;

        if (id == null) {
            method = "BAD REQUEST";
        }

        switch (method) {
            case "GET":
                resBody = read(id);
                break;
            case "POST":
                resBody = create(id, reqBody);
                break;
            case "PUT":
                resBody = update(id, reqBody);
                break;
            case "DELETE":
                resBody = delete(id);
                break;
            default:
                printManager.printLog(clientHost, requestLine, 400);
                exchange.sendResponseHeaders(400, 0);
                return;
        }

        exchange.sendResponseHeaders(200, resBody.toString().length());

        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(resBody, writer);
        writer.flush();
        writer.close();

        printManager.printLog(clientHost, requestLine, 200);
    }

    private String getIdFromPath(String path) {
        List<String> pathList = Arrays.asList(path.split("/"));

        if (pathList.size() <= 1) {
            return null;
        }

        return pathList.get(2);
    }

    private String getParamFromReqBody(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        while((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        return sb.toString();
    }

    private JsonElement parseParametersToJson(InputStream in) throws IOException {
        JsonObject json = new JsonObject();
        String param = getParamFromReqBody(in) + "&"; //for one parameter split

        Arrays.stream(param.split("&"))
                .forEach(field -> {
                    String[] keyAndValue = field.split("=");
                    json.addProperty(keyAndValue[0], keyAndValue[1]);
                });

        return json;
    }

    //POST Request
    private JsonElement create(String id, JsonElement body) {
        if (database.containsKey(id) || !body.getAsJsonObject().has(id)) {
            return gson.toJsonTree(Collections.singletonMap(id, "None"));
        }
        database.put(id, body.getAsJsonObject().get(id).getAsString());
        return gson.toJsonTree(Collections.singletonMap(id, database.get(id)));
    }

    //GET Request
    private JsonElement read(String id) {
        if (database.containsKey(id)) {
            return gson.toJsonTree(Collections.singletonMap(id, database.get(id)));
        }
        return gson.toJsonTree(Collections.singletonMap(id, "None"));
    }

    //PUT Request
    private JsonElement update(String id, JsonElement body) {
        if (database.containsKey(id) && body.getAsJsonObject().has(id)) {
            database.put(id, body.getAsJsonObject().get(id).getAsString());
            return gson.toJsonTree(Collections.singletonMap(id, database.get(id)));
        }
        return gson.toJsonTree(Collections.singletonMap(id, "None"));
    }

    //DELETE Request
    private JsonElement delete(String id) {
        if (database.containsKey(id)) {
            database.remove(id);
            return gson.toJsonTree(Collections.singletonMap(id, "Removed"));
        }
        return gson.toJsonTree(Collections.singletonMap(id, "None"));
    }
}

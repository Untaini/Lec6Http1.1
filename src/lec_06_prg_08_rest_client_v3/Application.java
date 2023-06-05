package lec_06_prg_08_rest_client_v3;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    private static final PrintStream out = System.out;

    public static void main(String[] args) {
        try {
            HttpResponse response;
            JsonElement json;

            response = get("http://127.0.0.1:5000/membership_api/0001");
            json = JsonParser.parseString(response.getResponseData());
            out.println("#1 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

            response = post("http://127.0.0.1:5000/membership_api/0001", Collections.singletonMap("0001", "apple"));
            json = JsonParser.parseString(response.getResponseData());
            out.println("#2 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

            response = get("http://127.0.0.1:5000/membership_api/0001");
            json = JsonParser.parseString(response.getResponseData());
            out.println("#3 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

            response = post("http://127.0.0.1:5000/membership_api/0001", Collections.singletonMap("0001", "xpple"));
            json = JsonParser.parseString(response.getResponseData());
            out.println("#4 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

            response = put("http://127.0.0.1:5000/membership_api/0002", Collections.singletonMap("0002", "xrange"));
            json = JsonParser.parseString(response.getResponseData());
            out.println("#5 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0002").getAsString());

            response = post("http://127.0.0.1:5000/membership_api/0002", Collections.singletonMap("0002", "xrange"));
            response = put("http://127.0.0.1:5000/membership_api/0002", Collections.singletonMap("0002", "orange"));
            json = JsonParser.parseString(response.getResponseData());
            out.println("#6 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0002").getAsString());

            response = delete("http://127.0.0.1:5000/membership_api/0001");
            json = JsonParser.parseString(response.getResponseData());
            out.println("#7 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

            response = delete("http://127.0.0.1:5000/membership_api/0001");
            json = JsonParser.parseString(response.getResponseData());
            out.println("#8 Code: " + response.getStatusCode() + " >> JSON: " + json.toString()
                    + " >> JSON Result: " + json.getAsJsonObject().get("0001").getAsString());

        } catch(Exception e) { e.printStackTrace(); }
    }

    private static HttpResponse get(String originUrl) throws Exception {
        return getResponseWithoutRequestBody(originUrl, "GET");
    }

    private static HttpResponse post(String originUrl, Map<String, String> requestBody) throws Exception {
        return getResponseWithRequestBody(originUrl, "POST", requestBody);
    }

    private static HttpResponse put(String originUrl, Map<String, String> requestBody) throws Exception {
        return getResponseWithRequestBody(originUrl, "PUT", requestBody);
    }

    private static HttpResponse delete(String originUrl) throws Exception {
        return getResponseWithoutRequestBody(originUrl, "DELETE");
    }

    private static HttpResponse getResponseWithoutRequestBody(String originUrl, String method) throws Exception {
        URL url = new URL(originUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(method);

        HttpResponse response = new HttpResponse(con.getResponseCode(), getResponseBodyContext(con));

        con.disconnect();

        return response;
    }

    private static HttpResponse getResponseWithRequestBody(String originUrl, String method, Map<String, String> requestBody) throws Exception {
        URL url = new URL(originUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        List<String> requestBodyParam = requestBody.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.toList());
        String requestBodyString = String.join("&", requestBodyParam);

        con.setDoOutput(true);
        con.setRequestMethod(method);

        con.getOutputStream().write(requestBodyString.getBytes(StandardCharsets.UTF_8));

        HttpResponse response = new HttpResponse(con.getResponseCode(), getResponseBodyContext(con));

        con.disconnect();

        return response;
    }

    private static String getResponseBodyContext(HttpURLConnection con) throws Exception {
        BufferedReader reader;
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        return responseBody.toString();
    }
}

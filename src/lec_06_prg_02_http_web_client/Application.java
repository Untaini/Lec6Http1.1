package lec_06_prg_02_http_web_client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    private static final PrintStream out = System.out;

    public static void main(String[] args) {
        try {
            out.println("## HTTP client started.");

            out.println("## GET request for http://0.0.0.0:8080/temp/");
            get("http://0.0.0.0:8080/temp/");

            out.println("## GET request for http://0.0.0.0:8080/?var1=9&var2=9");
            get("http://0.0.0.0:8080/?var1=9&var2=9");

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("var1", "9");
            requestBody.put("var2", "9");

            out.println("## POST request for http://0.0.0.0:8080/ with var1 is 9 and var2 is 9");
            post("http://0.0.0.0:8080/", requestBody);

            out.println("## HTTP client completed.");
        } catch(Exception e) { e.printStackTrace(); }
    }

    private static String get(String originUrl) throws Exception {
        URL url = new URL(originUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        out.println("## GET response [start]");
        String responseContext = getResponseBodyContext(con);
        out.println(responseContext);
        out.println("## GET response [end]");

        con.disconnect();

        return responseContext;
    }

    private static String post(String originUrl, Map<String, String> requestBody) throws Exception {
        URL url = new URL(originUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        List<String> requestBodyParam = requestBody.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.toList());
        String requestBodyString = String.join("&", requestBodyParam);

        con.setRequestMethod("POST");
        con.setDoOutput(true);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        writer.write(requestBodyString);
        writer.close();

        out.println("## POST response [start]");
        String responseContext = getResponseBodyContext(con);
        out.println(responseContext);
        out.println("## POST response [end]");

        con.disconnect();

        return responseContext;
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

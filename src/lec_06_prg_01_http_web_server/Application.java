package lec_06_prg_01_http_web_server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    private static final int PORT = 8080;
    private static final String HOST = "server";

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
            httpServer.createContext("/", new HttpCalcHandler(System.out));
            httpServer.start();

            System.out.printf("## HTTP server started at http://%s:%d.\n", HOST, PORT);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                httpServer.stop(0);
                System.out.println("## HTTP server stopped.");
            }));
        } catch (IOException ioe) {
            System.out.println("HTTP server error : " + ioe.getMessage());
        }
    }
}

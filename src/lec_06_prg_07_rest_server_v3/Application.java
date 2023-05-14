package lec_06_prg_07_rest_server_v3;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    private static final int PORT = 5000;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
            httpServer.createContext("/membership_api", new HttpMembershipHandler(System.out));
            httpServer.start();

            System.out.printf("## REST API server started at http://%s:%d.\n", HOST, PORT);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                httpServer.stop(0);
                System.out.println("## REST API server stopped.");
            }));
        } catch (IOException ioe) {
            System.out.println("REST API server error : " + ioe.getMessage());
        }
    }
}

package lec_06_prg_01_http_web_server;

import com.sun.net.httpserver.HttpExchange;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpPrintManager {
    private final PrintStream printStream;

    public HttpPrintManager(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printHttpRequestDetail(HttpExchange exchange) {
        InetSocketAddress clientAddress = exchange.getRemoteAddress();
        URI uri = exchange.getRequestURI();
        String requestLine =
                String.format("%s %s %s",exchange.getRequestMethod(), uri, exchange.getProtocol());

        printStream.println("::Client address   : " + clientAddress.getHostString());
        printStream.printf("::Client port      : %d\n", clientAddress.getPort());
        printStream.println("::Request command  : " + exchange.getRequestMethod());
        printStream.println("::Request line     : " + requestLine);
        printStream.println("::Request path     : " + uri);
        printStream.println("::Request version  : " + exchange.getProtocol());
    }

    public void printRequestData(String method, String param) {
        printStream.printf("## %s request data => %s.\n", method, param);
    }

    public void printCalcResponseData(String method, int var1, int var2, int result) {
        printStream.printf("## %s request for calculation => %d x %d = %d.\n", method, var1, var2, result);
    }

    public void printRequestPath(String path) {
        printStream.printf("## GET request for directory => %s.\n", path);
    }

    public void printGETDescription() {
        printStream.println("## GET method handled");
    }

    public void printPOSTDescription() {
        printStream.println("## POST method handled");
    }
}

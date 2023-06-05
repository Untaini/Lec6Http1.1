package lec_06_prg_07_rest_server_v3;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HttpPrintManager {

    private final PrintStream printStream;

    public HttpPrintManager(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printLog(String clientHost, String request, int code) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("[dd/MMM/yyyy hh:mm:ss]", new Locale("en")));

        printStream.printf("%s - - [%s] \"%s\" %d -\n", clientHost, time, request, code);
    }
}

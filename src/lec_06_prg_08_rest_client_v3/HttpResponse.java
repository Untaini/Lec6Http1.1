package lec_06_prg_08_rest_client_v3;

public class HttpResponse {
    private final int statusCode;
    private final String responseData;

    public HttpResponse(int statusCode, String responseData) {
        this.statusCode = statusCode;
        this.responseData = responseData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseData() {
        return responseData;
    }
}

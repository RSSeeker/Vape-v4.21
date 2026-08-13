package gg.vape.api;

public class ApiHttpStatusException
extends Exception {
    private final String url;
    private final String httpMethod;
    private final int statusCode;

    public String getUrl() {
        return this.url;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public ApiHttpStatusException(String url, String httpMethod, int statusCode) {
        super("Failed making Online request, URL: " + url + ", Action: " + httpMethod + ", Error Code: " + statusCode);
        this.url = url;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
    }
}

package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;
import java.util.stream.Collectors;

public abstract class ApiException extends Exception {
    private final int code;
    private final String url;
    private final String method;
    private final transient HttpHeaders responseHeaders;
    private final transient ExceptionResponse exceptionResponse;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.url = "";
        this.method = "";
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(Throwable throwable) {
        super(throwable);
        this.code = 0;
        this.url = "";
        this.method = "";
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(String message) {
        super(message);
        this.code = 0;
        this.url = "";
        this.method = "";
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(int code, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(message);
        this.code = code;
        this.url = "";
        this.method = "";
        this.exceptionResponse = exceptionResponse;
        this.responseHeaders = responseHeaders;
    }

    public ApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(message);
        this.code = code;
        this.url = url;
        this.method = method;
        this.exceptionResponse = exceptionResponse;
        this.responseHeaders = responseHeaders;
    }

    public int getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public String toString() {
        return "code=" + code +
                "\nurl=" + url +
                "\nmethod=" + method +
                ",\nresponseHeaders=" + (responseHeaders != null
                ? responseHeaders.map().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(value -> "'" + entry.getKey() + ": " + value + "'"))
                .collect(Collectors.joining(", ")) : responseHeaders) +
                ",\n" + exceptionResponse +
                ",\nmessage=" + getMessage();
    }
}

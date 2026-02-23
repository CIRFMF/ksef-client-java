package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;
import java.util.stream.Collectors;

public class ApiException extends Exception {
    private final int code;
    private final transient HttpHeaders responseHeaders;
    private final transient ExceptionResponse exceptionResponse;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(Throwable throwable) {
        super(throwable);
        this.code = 0;
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(String message) {
        super(message);
        this.code = 0;
        this.responseHeaders = null;
        this.exceptionResponse = null;
    }

    public ApiException(int code, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(message);
        this.code = code;
        this.exceptionResponse = exceptionResponse;
        this.responseHeaders = responseHeaders;
    }

    public int getCode() {
        return code;
    }

    public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "\ncode=" + code +
                ",\nresponseHeaders=" + (responseHeaders != null
                ? responseHeaders.map().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(value -> "'" + entry.getKey() + ": " + value + "'"))
                .collect(Collectors.joining(", ")) : responseHeaders) +
                ",\n" + exceptionResponse +
                "\n}";
    }
}

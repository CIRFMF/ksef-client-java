package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;

public class KsefApiException extends ApiException {

    public KsefApiException(int code, String message) {
        super(code, message);
    }

    public KsefApiException(Throwable throwable) {
        super(throwable);
    }

    public KsefApiException(String message) {
        super(message);
    }

    public KsefApiException(int code, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(code, message, responseHeaders, exceptionResponse);
    }

    public KsefApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, ExceptionResponse exceptionResponse) {
        super(code, url, method, message, responseHeaders, exceptionResponse);
    }

    @Override
    public String toString() {
        return "KsefApiException{" + super.toString() + "}";
    }
}

package pl.akmf.ksef.sdk.client.model.exceptions;

import pl.akmf.ksef.sdk.client.model.ApiException;

import java.net.http.HttpHeaders;

public class GoneApiException extends ApiException {

    private final GoneProblemDetails goneProblemDetails;

    public GoneApiException(int code, String message, GoneProblemDetails goneProblemDetails) {
        super(code, message);
        this.goneProblemDetails = goneProblemDetails;
    }

    public GoneApiException(Throwable throwable, GoneProblemDetails goneProblemDetails) {
        super(throwable);
        this.goneProblemDetails = goneProblemDetails;
    }

    public GoneApiException(String message, GoneProblemDetails goneProblemDetails) {
        super(message);
        this.goneProblemDetails = goneProblemDetails;
    }

    public GoneApiException(int code, String message, HttpHeaders responseHeaders, GoneProblemDetails goneProblemDetails) {
        super(code, message, responseHeaders, null);
        this.goneProblemDetails = goneProblemDetails;
    }

    public GoneApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, GoneProblemDetails goneProblemDetails) {
        super(code, url, method, message, responseHeaders, null);
        this.goneProblemDetails = goneProblemDetails;
    }

    public GoneProblemDetails getGoneProblemDetails() {
        return goneProblemDetails;
    }

    @Override
    public String toString() {
        return "GoneApiException{" +
                "goneProblemDetails=" + goneProblemDetails +
                ", " + super.toString() +
                '}';
    }
}

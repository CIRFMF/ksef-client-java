package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;

public class ForbiddenApiException extends ApiException {

    private final ForbiddenProblemDetails forbiddenProblemDetails;

    public ForbiddenApiException(int code, String message, ForbiddenProblemDetails forbiddenProblemDetails) {
        super(code, message);
        this.forbiddenProblemDetails = forbiddenProblemDetails;
    }

    public ForbiddenApiException(Throwable throwable, ForbiddenProblemDetails forbiddenProblemDetails) {
        super(throwable);
        this.forbiddenProblemDetails = forbiddenProblemDetails;
    }

    public ForbiddenApiException(String message, ForbiddenProblemDetails forbiddenProblemDetails) {
        super(message);
        this.forbiddenProblemDetails = forbiddenProblemDetails;
    }

    public ForbiddenApiException(int code, String message, HttpHeaders responseHeaders, ForbiddenProblemDetails forbiddenProblemDetails) {
        super(code, message, responseHeaders, null);
        this.forbiddenProblemDetails = forbiddenProblemDetails;
    }

    public ForbiddenApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, ForbiddenProblemDetails forbiddenProblemDetails) {
        super(code, url, method, message, responseHeaders, null);
        this.forbiddenProblemDetails = forbiddenProblemDetails;
    }

    public ForbiddenProblemDetails getForbiddenProblemDetails() {
        return forbiddenProblemDetails;
    }

    @Override
    public String toString() {
        return "ForbiddenApiException{" +
                "forbiddenProblemDetails=" + forbiddenProblemDetails +
                ", " + super.toString() +
                '}';
    }
}

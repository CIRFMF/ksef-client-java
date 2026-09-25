package pl.akmf.ksef.sdk.client.model;

import java.net.http.HttpHeaders;

/**
 * Wyjątek zgłaszany przez klienta SDK, gdy API KSeF zwróci odpowiedź HTTP 401 (Unauthorized).
 * Przenosi szczegóły błędu jako {@link UnauthorizedProblemDetails}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class UnauthorizedApiException extends ApiException {

    private final UnauthorizedProblemDetails unauthorizedProblemDetails;

    public UnauthorizedApiException(int code, String message, UnauthorizedProblemDetails unauthorizedProblemDetails) {
        super(code, message);
        this.unauthorizedProblemDetails = unauthorizedProblemDetails;
    }

    public UnauthorizedApiException(Throwable throwable, UnauthorizedProblemDetails unauthorizedProblemDetails) {
        super(throwable);
        this.unauthorizedProblemDetails = unauthorizedProblemDetails;
    }

    public UnauthorizedApiException(String message, UnauthorizedProblemDetails unauthorizedProblemDetails) {
        super(message);
        this.unauthorizedProblemDetails = unauthorizedProblemDetails;
    }

    public UnauthorizedApiException(int code, String message, HttpHeaders responseHeaders, UnauthorizedProblemDetails unauthorizedProblemDetails) {
        super(code, message, responseHeaders, null);
        this.unauthorizedProblemDetails = unauthorizedProblemDetails;
    }

    public UnauthorizedApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, UnauthorizedProblemDetails unauthorizedProblemDetails) {
        super(code, url, method, message, responseHeaders, null);
        this.unauthorizedProblemDetails = unauthorizedProblemDetails;
    }

    public UnauthorizedProblemDetails getUnauthorizedProblemDetails() {
        return unauthorizedProblemDetails;
    }

    @Override
    public String toString() {
        return "UnauthorizedApiException{" + "unauthorizedProblemDetails=" + unauthorizedProblemDetails + ", " + super.toString() + '}';
    }
}

package pl.akmf.ksef.sdk.client.model.exceptions;

import pl.akmf.ksef.sdk.client.model.ApiException;
import java.net.http.HttpHeaders;

/**
 * Wyjątek zgłaszany przez klienta SDK, gdy API KSeF zwróci odpowiedź HTTP 400 (Bad Request).
 * Przenosi szczegóły błędu jako {@link BadRequestProblemDetails}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BadRequestApiException extends ApiException {

    private final BadRequestProblemDetails badRequestProblemDetails;

    public BadRequestApiException(int code, String message, BadRequestProblemDetails badRequestProblemDetails) {
        super(code, message);
        this.badRequestProblemDetails = badRequestProblemDetails;
    }

    public BadRequestApiException(Throwable throwable, BadRequestProblemDetails badRequestProblemDetails) {
        super(throwable);
        this.badRequestProblemDetails = badRequestProblemDetails;
    }

    public BadRequestApiException(String message, BadRequestProblemDetails badRequestProblemDetails) {
        super(message);
        this.badRequestProblemDetails = badRequestProblemDetails;
    }

    public BadRequestApiException(int code, String message, HttpHeaders responseHeaders, BadRequestProblemDetails badRequestProblemDetails) {
        super(code, message, responseHeaders, null);
        this.badRequestProblemDetails = badRequestProblemDetails;
    }

    public BadRequestApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, BadRequestProblemDetails badRequestProblemDetails) {
        super(code, url, method, message, responseHeaders, null);
        this.badRequestProblemDetails = badRequestProblemDetails;
    }

    public BadRequestProblemDetails getBadRequestProblemDetails() {
        return badRequestProblemDetails;
    }

    @Override
    public String toString() {
        return "BadRequestApiException{" + "badRequestProblemDetails=" + badRequestProblemDetails + ", " + super.toString() + '}';
    }
}

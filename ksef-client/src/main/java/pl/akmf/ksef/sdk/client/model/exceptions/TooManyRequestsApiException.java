package pl.akmf.ksef.sdk.client.model.exceptions;

import pl.akmf.ksef.sdk.client.model.ApiException;
import java.net.http.HttpHeaders;

/**
 * Wyjątek zgłaszany przez klienta SDK, gdy API KSeF zwróci odpowiedź HTTP 429 (Too Many Requests).
 * Przenosi szczegóły błędu jako {@link TooManyRequestsProblemDetails}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TooManyRequestsApiException extends ApiException {

    private final TooManyRequestsProblemDetails tooManyRequestsProblemDetails;

    public TooManyRequestsApiException(int code, String message, TooManyRequestsProblemDetails tooManyRequestsProblemDetails) {
        super(code, message);
        this.tooManyRequestsProblemDetails = tooManyRequestsProblemDetails;
    }

    public TooManyRequestsApiException(Throwable throwable, TooManyRequestsProblemDetails tooManyRequestsProblemDetails) {
        super(throwable);
        this.tooManyRequestsProblemDetails = tooManyRequestsProblemDetails;
    }

    public TooManyRequestsApiException(String message, TooManyRequestsProblemDetails tooManyRequestsProblemDetails) {
        super(message);
        this.tooManyRequestsProblemDetails = tooManyRequestsProblemDetails;
    }

    public TooManyRequestsApiException(int code, String message, HttpHeaders responseHeaders, TooManyRequestsProblemDetails tooManyRequestsProblemDetails) {
        super(code, message, responseHeaders, null);
        this.tooManyRequestsProblemDetails = tooManyRequestsProblemDetails;
    }

    public TooManyRequestsApiException(int code, String url, String method, String message, HttpHeaders responseHeaders, TooManyRequestsProblemDetails tooManyRequestsProblemDetails) {
        super(code, url, method, message, responseHeaders, null);
        this.tooManyRequestsProblemDetails = tooManyRequestsProblemDetails;
    }

    public TooManyRequestsProblemDetails getTooManyRequestsProblemDetails() {
        return tooManyRequestsProblemDetails;
    }

    @Override
    public String toString() {
        return "TooManyRequestsApiException{" + "tooManyRequestsProblemDetails=" + tooManyRequestsProblemDetails + ", " + super.toString() + '}';
    }
}

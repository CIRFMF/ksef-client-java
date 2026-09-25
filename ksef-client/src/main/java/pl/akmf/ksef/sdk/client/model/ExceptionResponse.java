package pl.akmf.ksef.sdk.client.model;

/**
 * ExceptionResponse.
 *
 * Reprezentuje ustrukturyzowaną odpowiedź błędu zwracaną przez interfejs API.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ExceptionResponse {

    /**
     * Zawiera główną treść wyjątku wraz ze szczegółami.
     */
    private ExceptionObject exception;

    /**
     * Reprezentuje wyjątek ograniczenia częstotliwości (HTTP 429 Too Many Requests) z API KSeF.
     * Zawiera informacje o ponownych próbach z nagłówka Retry-After.
     */
    private TooManyRequestsResponse status;

    public ExceptionResponse() {
    }

    public ExceptionResponse(ExceptionObject exception) {
        this.exception = exception;
    }

    /**
     * Zawiera główną treść wyjątku wraz ze szczegółami.
     */
    public ExceptionObject getException() {
        return exception;
    }

    /**
     * Zawiera główną treść wyjątku wraz ze szczegółami.
     */
    public void setException(ExceptionObject exception) {
        this.exception = exception;
    }

    /**
     * Reprezentuje wyjątek ograniczenia częstotliwości (HTTP 429 Too Many Requests) z API KSeF.
     * Zawiera informacje o ponownych próbach z nagłówka Retry-After.
     */
    public TooManyRequestsResponse getStatus() {
        return status;
    }

    /**
     * Reprezentuje wyjątek ograniczenia częstotliwości (HTTP 429 Too Many Requests) z API KSeF.
     * Zawiera informacje o ponownych próbach z nagłówka Retry-After.
     */
    public void setStatus(TooManyRequestsResponse status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return exception != null ? (" exception{" + exception + "}") : "" + status != null ? ("status{" + status + "}") : "";
    }
}

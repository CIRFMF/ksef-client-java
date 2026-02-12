package pl.akmf.ksef.sdk.client.model;

// Reprezentuje ustrukturyzowaną odpowiedź błędu zwracaną przez interfejs API.
public class ExceptionResponse {
    // Zawiera główną treść wyjątku wraz ze szczegółami.
    private ExceptionObject exception;
    // Reprezentuje wyjątek ograniczenia częstotliwości (HTTP 429 Too Many Requests) z API KSeF.
    // Zawiera informacje o ponownych próbach z nagłówka Retry-After.
    private TooManyRequestsResponse status;

    public ExceptionResponse() {
    }

    public ExceptionResponse(ExceptionObject exception) {
        this.exception = exception;
    }

    public ExceptionObject getException() {
        return exception;
    }

    public void setException(ExceptionObject exception) {
        this.exception = exception;
    }

    public TooManyRequestsResponse getStatus() {
        return status;
    }

    public void setStatus(TooManyRequestsResponse status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return exception != null ? (" exception{" + exception + "}") : "" +
                status != null ? ("status{" + status + "}") : "";
    }

}

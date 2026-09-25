package pl.akmf.ksef.sdk.client.model.exceptions;

/**
 * TooManyRequestsProblemDetails.
 * Reprezentuje odpowiedź Problem Details (application/problem+json) dla błędów HTTP 429 Too Many Requests.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TooManyRequestsProblemDetails {

    /**
     * Krótki, czytelny tytuł błędu (np. "Too Many Requests").
     */
    private String title;

    /**
     * Kod statusu HTTP (429).
     */
    private int status;

    /**
     * URI identyfikujące konkretne wystąpienie błędu.
     */
    private String instance;

    /**
     * Informacja o przyczynie przekroczenia limitu żądań oraz wskazówki dotyczące ponowienia żądania.
     */
    private String detail;

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    private String timestamp;

    /**
     * Identyfikator śledzenia błędu.
     */
    private String traceId;

    public TooManyRequestsProblemDetails() {
    }

    public TooManyRequestsProblemDetails(String title, int status, String instance, String detail, String timestamp, String traceId) {
        this.title = title;
        this.status = status;
        this.instance = instance;
        this.detail = detail;
        this.timestamp = timestamp;
        this.traceId = traceId;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Too Many Requests").
     */
    public String getTitle() {
        return title;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Too Many Requests").
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Kod statusu HTTP (429).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Kod statusu HTTP (429).
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * URI identyfikujące konkretne wystąpienie błędu.
     */
    public String getInstance() {
        return instance;
    }

    /**
     * URI identyfikujące konkretne wystąpienie błędu.
     */
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * Informacja o przyczynie przekroczenia limitu żądań oraz wskazówki dotyczące ponowienia żądania.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Informacja o przyczynie przekroczenia limitu żądań oraz wskazówki dotyczące ponowienia żądania.
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Identyfikator śledzenia błędu.
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Identyfikator śledzenia błędu.
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "title='" + title + ", status=" + status + ", instance='" + instance + ", detail='" + detail + ", timestamp='" + timestamp + ", traceId='" + traceId;
    }
}

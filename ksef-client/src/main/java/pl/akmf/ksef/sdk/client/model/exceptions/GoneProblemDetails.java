package pl.akmf.ksef.sdk.client.model.exceptions;

/**
 * GoneProblemDetails.
 * Reprezentuje odpowiedź Problem Details (application/problem+json) dla błędów HTTP 410 Gone.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GoneProblemDetails {

    /**
     * Krótki, czytelny tytuł błędu (np. "Gone").
     */
    private String title;

    /**
     * Kod statusu HTTP (410).
     */
    private int status;

    /**
     * URI identyfikujące konkretne wystąpienie błędu.
     */
    private String instance;

    /**
     * Ogólny opis problemu.
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

    public GoneProblemDetails() {
    }

    public GoneProblemDetails(String title, int status, String instance, String detail, String timestamp, String traceId) {
        this.title = title;
        this.status = status;
        this.instance = instance;
        this.detail = detail;
        this.timestamp = timestamp;
        this.traceId = traceId;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Gone").
     */
    public String getTitle() {
        return title;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Gone").
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Kod statusu HTTP (410).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Kod statusu HTTP (410).
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
     * Ogólny opis problemu.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Ogólny opis problemu.
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

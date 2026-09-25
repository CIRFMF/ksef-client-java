package pl.akmf.ksef.sdk.client.model;

/**
 * UnauthorizedProblemDetails.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class UnauthorizedProblemDetails {

    /**
     * Unauthorized
     */
    private String title;

    /**
     * 401
     */
    private int status;

    /**
     * Szczegółowy opis przyczyny odmowy dostępu.
     */
    private String detail;

    /**
     * URI identyfikujące konkretne wystąpienie błędu.
     */
    private String instance;

    /**
     * Identyfikator śledzenia błędu.
     */
    private String traceId;

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    private String timestamp;

    public UnauthorizedProblemDetails() {
    }

    public UnauthorizedProblemDetails(String title, int status, String detail, String instance, String traceId) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.traceId = traceId;
    }

    public UnauthorizedProblemDetails(String title, int status, String detail, String instance, String traceId, String timestamp) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.traceId = traceId;
        this.timestamp = timestamp;
    }

    /**
     * Unauthorized
     */
    public String getTitle() {
        return title;
    }

    /**
     * Unauthorized
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 401
     */
    public int getStatus() {
        return status;
    }

    /**
     * 401
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Szczegółowy opis przyczyny odmowy dostępu.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Szczegółowy opis przyczyny odmowy dostępu.
     */
    public void setDetail(String detail) {
        this.detail = detail;
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

    @Override
    public String toString() {
        return "{title=" + title + ", status=" + status + ", detail=" + detail + ", instance=" + instance + ", traceId=" + traceId + ", timestamp=" + timestamp + "}";
    }
}

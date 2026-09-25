package pl.akmf.ksef.sdk.client.model;

import java.util.Map;

/**
 * ForbiddenProblemDetails.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ForbiddenProblemDetails {

    /**
     * Forbidden
     */
    private String title;

    /**
     * 403
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
     * Kod przyczyny odmowy dostępu.
     */
    private String reasonCode;

    /**
     * Dodatkowe dane zależne od {@code reasonCode}.
     */
    private Map<String, Object> security;

    /**
     * Identyfikator śledzenia błędu.
     */
    private String traceId;

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    private String timestamp;

    public ForbiddenProblemDetails() {
    }

    public ForbiddenProblemDetails(String title, int status, String detail, String instance, String reasonCode, Map<String, Object> security, String traceId) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.reasonCode = reasonCode;
        this.security = security;
        this.traceId = traceId;
    }

    public ForbiddenProblemDetails(String title, int status, String detail, String instance, String reasonCode, Map<String, Object> security, String traceId, String timestamp) {
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.reasonCode = reasonCode;
        this.security = security;
        this.traceId = traceId;
        this.timestamp = timestamp;
    }

    /**
     * Forbidden
     */
    public String getTitle() {
        return title;
    }

    /**
     * Forbidden
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 403
     */
    public int getStatus() {
        return status;
    }

    /**
     * 403
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
     * Kod przyczyny odmowy dostępu.
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Kod przyczyny odmowy dostępu.
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * Dodatkowe dane zależne od {@code reasonCode}.
     */
    public Map<String, Object> getSecurity() {
        return security;
    }

    /**
     * Dodatkowe dane zależne od {@code reasonCode}.
     */
    public void setSecurity(Map<String, Object> security) {
        this.security = security;
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
        return "title=" + title + ", status=" + status + ", detail=" + detail + ", instance=" + instance + ", reasonCode=" + reasonCode + ", security=" + security + ", timestamp=" + timestamp + ", traceId=" + traceId;
    }
}

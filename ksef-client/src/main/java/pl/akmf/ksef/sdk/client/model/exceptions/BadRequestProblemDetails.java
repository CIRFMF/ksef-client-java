package pl.akmf.ksef.sdk.client.model.exceptions;

import java.util.List;

/**
 * BadRequestProblemDetails.
 * Reprezentuje odpowiedź Problem Details (application/problem+json) dla błędów HTTP 400 Bad Request.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BadRequestProblemDetails {

    /**
     * Krótki, czytelny tytuł błędu (np. "Bad Request").
     */
    private String title;

    /**
     * Kod statusu HTTP (400).
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
     * Lista błędów powiązanych z żądaniem.
     */
    private List<BadRequestApiError> errors;

    /**
     * Data i czas wystąpienia błędu w UTC.
     */
    private String timestamp;

    /**
     * Identyfikator śledzenia błędu.
     */
    private String traceId;

    public BadRequestProblemDetails() {
    }

    public BadRequestProblemDetails(String title, int status, String instance, String detail, List<BadRequestApiError> errors, String timestamp, String traceId) {
        this.title = title;
        this.status = status;
        this.instance = instance;
        this.detail = detail;
        this.errors = errors;
        this.timestamp = timestamp;
        this.traceId = traceId;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Bad Request").
     */
    public String getTitle() {
        return title;
    }

    /**
     * Krótki, czytelny tytuł błędu (np. "Bad Request").
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Kod statusu HTTP (400).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Kod statusu HTTP (400).
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
     * Lista błędów powiązanych z żądaniem.
     */
    public List<BadRequestApiError> getErrors() {
        return errors;
    }

    /**
     * Lista błędów powiązanych z żądaniem.
     */
    public void setErrors(List<BadRequestApiError> errors) {
        this.errors = errors;
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
        return "title='" + title + ", status=" + status + ", instance='" + instance + ", detail='" + detail + ", errors=" + errors + ", timestamp='" + timestamp + ", traceId='" + traceId;
    }
}

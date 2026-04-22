package pl.akmf.ksef.sdk.client.model.exceptions;

import java.util.List;

// Reprezentuje odpowiedź Problem Details (application/problem+json) dla błędów HTTP 400 Bad Request.
public class BadRequestProblemDetails {

    // Krótki, czytelny tytuł błędu (np. "Bad Request").
    private String title;

    // Kod statusu HTTP (400).
    private int status;

    // URI identyfikujące konkretne wystąpienie błędu.
    private String instance;

    // Ogólny opis problemu.
    private String detail;

    // Lista błędów powiązanych z żądaniem.
    private List<BadRequestApiError> errors;

    // Data i czas wystąpienia błędu w UTC.
    private String timestamp;

    // Identyfikator śledzenia błędu.
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<BadRequestApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<BadRequestApiError> errors) {
        this.errors = errors;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "title='" + title +
                ", status=" + status +
                ", instance='" + instance +
                ", detail='" + detail +
                ", errors=" + errors +
                ", timestamp='" + timestamp +
                ", traceId='" + traceId;
    }
}

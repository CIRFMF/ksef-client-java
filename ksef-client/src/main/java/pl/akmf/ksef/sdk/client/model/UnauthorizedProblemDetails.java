package pl.akmf.ksef.sdk.client.model;

public class UnauthorizedProblemDetails {

    private String title;
    private int status;
    private String detail;
    private String instance;
    private String traceId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{title=" + title +
                ", status=" + status +
                ", detail=" + detail +
                ", instance=" + instance +
                ", traceId=" + traceId +
                ", timestamp=" + timestamp
                + "}";
    }
}

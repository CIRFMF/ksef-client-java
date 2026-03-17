package pl.akmf.ksef.sdk.client.model;

import java.util.Map;

public class ForbiddenProblemDetails {
    private String title;
    private int status;
    private String detail;
    private String instance;
    private String reasonCode;
    private Map<String, Object> security;
    private String traceId;

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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Map<String, Object> getSecurity() {
        return security;
    }

    public void setSecurity(Map<String, Object> security) {
        this.security = security;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "title=" + title +
                ", status=" + status +
                ", detail=" + detail +
                ", instance=" + instance +
                ", reasonCode=" + reasonCode +
                ", security=" + security +
                ", traceId=" + traceId;
    }
}

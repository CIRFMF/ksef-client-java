package pl.akmf.ksef.sdk.client.model;

import pl.akmf.ksef.sdk.client.ExceptionDetails;

import java.time.OffsetDateTime;
import java.util.List;

public class ExceptionObject {
    private List<ExceptionDetails> exceptionDetailList;
    private String referenceNumber;
    private String serviceCode;
    private String serviceCtx;
    private String serviceName;
    private OffsetDateTime timestamp;

    public ExceptionObject() {

    }

    public List<ExceptionDetails> getExceptionDetailList() {
        return exceptionDetailList;
    }

    public void setExceptionDetailList(List<ExceptionDetails> exceptionDetailList) {
        this.exceptionDetailList = exceptionDetailList;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceCtx() {
        return serviceCtx;
    }

    public void setServiceCtx(String serviceCtx) {
        this.serviceCtx = serviceCtx;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

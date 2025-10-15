package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;

import java.time.OffsetDateTime;


/**
 * SessionsQueryResponseItem
 */
public class SessionsQueryResponseItem {
    private String referenceNumber;

    private StatusInfo status;

    private OffsetDateTime dateCreated;

    private OffsetDateTime dateUpdated;

    private OffsetDateTime validUntil;

    private Integer totalInvoiceCount;

    private Integer successfulInvoiceCount;

    private Integer failedInvoiceCount;

    public SessionsQueryResponseItem() {
    }


    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusInfo getStatus() {
        return status;
    }

    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(OffsetDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public Integer getTotalInvoiceCount() {
        return totalInvoiceCount;
    }

    public void setTotalInvoiceCount(Integer totalInvoiceCount) {
        this.totalInvoiceCount = totalInvoiceCount;
    }

    public Integer getSuccessfulInvoiceCount() {
        return successfulInvoiceCount;
    }

    public void setSuccessfulInvoiceCount(Integer successfulInvoiceCount) {
        this.successfulInvoiceCount = successfulInvoiceCount;
    }

    public Integer getFailedInvoiceCount() {
        return failedInvoiceCount;
    }

    public void setFailedInvoiceCount(Integer failedInvoiceCount) {
        this.failedInvoiceCount = failedInvoiceCount;
    }
}


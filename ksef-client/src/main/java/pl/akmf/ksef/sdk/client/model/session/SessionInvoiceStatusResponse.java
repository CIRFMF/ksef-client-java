package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicingMode;

import java.time.OffsetDateTime;

public class SessionInvoiceStatusResponse {
    private Integer ordinalNumber;
    private String invoiceNumber;
    private String ksefNumber;
    private String referenceNumber;
    private String invoiceHash;
    private String invoiceFileName;
    private OffsetDateTime invoicingDate;
    private OffsetDateTime acquisitionDate;
    private StatusInfo status;
    private OffsetDateTime permanentStorageDate;
    private String upoDownloadUrl;
    private InvoicingMode invoicingMode;

    public SessionInvoiceStatusResponse() {

    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getKsefNumber() {
        return ksefNumber;
    }

    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getInvoiceHash() {
        return invoiceHash;
    }

    public void setInvoiceHash(String invoiceHash) {
        this.invoiceHash = invoiceHash;
    }

    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }

    public OffsetDateTime getInvoicingDate() {
        return invoicingDate;
    }

    public void setInvoicingDate(OffsetDateTime invoicingDate) {
        this.invoicingDate = invoicingDate;
    }

    public OffsetDateTime getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(OffsetDateTime acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public StatusInfo getStatus() {
        return status;
    }

    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    public OffsetDateTime getPermanentStorageDate() {
        return permanentStorageDate;
    }

    public void setPermanentStorageDate(OffsetDateTime permanentStorageDate) {
        this.permanentStorageDate = permanentStorageDate;
    }

    public String getUpoDownloadUrl() {
        return upoDownloadUrl;
    }

    public void setUpoDownloadUrl(String upoDownloadUrl) {
        this.upoDownloadUrl = upoDownloadUrl;
    }

    public InvoicingMode getInvoicingMode() {
        return invoicingMode;
    }

    public void setInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
    }
}


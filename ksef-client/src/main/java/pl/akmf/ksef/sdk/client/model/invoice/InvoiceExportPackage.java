package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;
import java.util.List;

public class InvoiceExportPackage {
    private int invoiceCount;
    private int size;
    private List<InvoicePackagePart> parts;
    private Boolean isTruncated;
    private OffsetDateTime lastIssueDate;
    private OffsetDateTime lastInvoicingDate;
    private OffsetDateTime lastPermanentStorageDate;

    public InvoiceExportPackage() {

    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<InvoicePackagePart> getParts() {
        return parts;
    }

    public void setParts(List<InvoicePackagePart> parts) {
        this.parts = parts;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public OffsetDateTime getLastIssueDate() {
        return lastIssueDate;
    }

    public void setLastIssueDate(OffsetDateTime lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    public OffsetDateTime getLastInvoicingDate() {
        return lastInvoicingDate;
    }

    public void setLastInvoicingDate(OffsetDateTime lastInvoicingDate) {
        this.lastInvoicingDate = lastInvoicingDate;
    }

    public OffsetDateTime getLastPermanentStorageDate() {
        return lastPermanentStorageDate;
    }

    public void setLastPermanentStorageDate(OffsetDateTime lastPermanentStorageDate) {
        this.lastPermanentStorageDate = lastPermanentStorageDate;
    }
}

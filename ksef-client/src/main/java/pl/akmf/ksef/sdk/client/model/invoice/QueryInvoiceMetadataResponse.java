package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class QueryInvoiceMetadataResponse {
    private Boolean hasMore;
    private Boolean isTruncated;
    private List<InvoiceMetadata> invoices = new ArrayList<>();
    // Górna granica daty PermanentStorage (UTC), do której system uwzględnił dane w ramach tego zapytania - HWM (high water mark).
    private OffsetDateTime permanentStorageHwmDate;

    public QueryInvoiceMetadataResponse() {
    }

    public QueryInvoiceMetadataResponse(Boolean hasMore, List<InvoiceMetadata> invoices, Boolean isTruncated) {
        this.hasMore = hasMore;
        this.invoices = invoices;
        this.isTruncated = isTruncated;
    }

    public QueryInvoiceMetadataResponse(Boolean hasMore, Boolean isTruncated, List<InvoiceMetadata> invoices, OffsetDateTime permanentStorageHwmDate) {
        this.hasMore = hasMore;
        this.isTruncated = isTruncated;
        this.invoices = invoices;
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }

    public List<InvoiceMetadata> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceMetadata> invoices) {
        this.invoices = invoices;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public OffsetDateTime getPermanentStorageHwmDate() {
        return permanentStorageHwmDate;
    }

    public void setPermanentStorageHwmDate(OffsetDateTime permanentStorageHwmDate) {
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }
}


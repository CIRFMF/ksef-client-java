package pl.akmf.ksef.sdk.client.model.invoice;

import java.util.ArrayList;
import java.util.List;

public class QueryInvoiceMetadataResponse {
    private Boolean hasMore;
    private Boolean isTruncated;
    private List<InvoiceMetadata> invoices = new ArrayList<>();

    public QueryInvoiceMetadataResponse() {
    }

    public QueryInvoiceMetadataResponse(Boolean hasMore, List<InvoiceMetadata> invoices, Boolean isTruncated) {
        this.hasMore = hasMore;
        this.invoices = invoices;
        this.isTruncated = isTruncated;
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
}


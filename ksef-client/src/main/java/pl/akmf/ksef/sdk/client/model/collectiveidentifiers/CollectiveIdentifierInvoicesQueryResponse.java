package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

public class CollectiveIdentifierInvoicesQueryResponse {

    private String continuationToken;
    private List<CollectiveIdentifierInvoicesQueryResponseItem> invoices;

    public CollectiveIdentifierInvoicesQueryResponse() {
    }

    public CollectiveIdentifierInvoicesQueryResponse(String continuationToken, List<CollectiveIdentifierInvoicesQueryResponseItem> invoices) {
        this.continuationToken = continuationToken;
        this.invoices = invoices;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<CollectiveIdentifierInvoicesQueryResponseItem> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<CollectiveIdentifierInvoicesQueryResponseItem> invoices) {
        this.invoices = invoices;
    }
}

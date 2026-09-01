package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

public class GenerateCollectiveIdentifierRequest {

    private List<CollectiveIdentifierInvoice> invoices;

    public GenerateCollectiveIdentifierRequest() {
    }

    public GenerateCollectiveIdentifierRequest(List<CollectiveIdentifierInvoice> invoices) {
        this.invoices = invoices;
    }

    public List<CollectiveIdentifierInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<CollectiveIdentifierInvoice> invoices) {
        this.invoices = invoices;
    }
}

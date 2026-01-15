package pl.akmf.ksef.sdk.client.model.invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoicePackageMetadata {
    private List<InvoiceMetadata> invoices = new ArrayList<>();

    public InvoicePackageMetadata() {
    }

    public InvoicePackageMetadata(List<InvoiceMetadata> invoices) {
        this.invoices = invoices;
    }

    public List<InvoiceMetadata> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceMetadata> invoices) {
        this.invoices = invoices;
    }
}
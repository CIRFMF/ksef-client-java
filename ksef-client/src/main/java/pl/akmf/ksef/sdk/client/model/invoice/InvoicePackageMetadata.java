package pl.akmf.ksef.sdk.client.model.invoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Wewnętrzny typ klienta SDK opisujący listę metadanych faktur wchodzących w skład
 * paczki wsadowej/eksportu (odpowiednik pliku metadanych wewnątrz paczki ZIP).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
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

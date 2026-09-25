package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

/**
 * GenerateCollectiveIdentifierRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GenerateCollectiveIdentifierRequest {

    /**
     * Lista faktur wchodząca w skład identyfikatora zbiorczego. Domyślny <a href="https://github.com/CIRFMF/ksef-api/blob/main/limity/limity.md">limit</a> faktur wynosi 500.
     */
    private List<CollectiveIdentifierInvoice> invoices;

    public GenerateCollectiveIdentifierRequest() {
    }

    public GenerateCollectiveIdentifierRequest(List<CollectiveIdentifierInvoice> invoices) {
        this.invoices = invoices;
    }

    /**
     * Lista faktur wchodząca w skład identyfikatora zbiorczego. Domyślny <a href="https://github.com/CIRFMF/ksef-api/blob/main/limity/limity.md">limit</a> faktur wynosi 500.
     */
    public List<CollectiveIdentifierInvoice> getInvoices() {
        return invoices;
    }

    /**
     * Lista faktur wchodząca w skład identyfikatora zbiorczego. Domyślny <a href="https://github.com/CIRFMF/ksef-api/blob/main/limity/limity.md">limit</a> faktur wynosi 500.
     */
    public void setInvoices(List<CollectiveIdentifierInvoice> invoices) {
        this.invoices = invoices;
    }
}

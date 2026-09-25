package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

/**
 * CollectiveIdentifierInvoicesQueryResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoicesQueryResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista faktur.
     */
    private List<CollectiveIdentifierInvoicesQueryResponseItem> invoices;

    public CollectiveIdentifierInvoicesQueryResponse() {
    }

    public CollectiveIdentifierInvoicesQueryResponse(String continuationToken, List<CollectiveIdentifierInvoicesQueryResponseItem> invoices) {
        this.continuationToken = continuationToken;
        this.invoices = invoices;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public String getContinuationToken() {
        return continuationToken;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    /**
     * Lista faktur.
     */
    public List<CollectiveIdentifierInvoicesQueryResponseItem> getInvoices() {
        return invoices;
    }

    /**
     * Lista faktur.
     */
    public void setInvoices(List<CollectiveIdentifierInvoicesQueryResponseItem> invoices) {
        this.invoices = invoices;
    }
}

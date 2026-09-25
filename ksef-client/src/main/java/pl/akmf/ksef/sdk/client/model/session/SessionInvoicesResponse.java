package pl.akmf.ksef.sdk.client.model.session;

import java.util.List;

/**
 * SessionInvoicesResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SessionInvoicesResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista pobranych faktur.
     */
    private List<SessionInvoiceStatusResponse> invoices;

    public SessionInvoicesResponse() {
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
     * Lista pobranych faktur.
     */
    public List<SessionInvoiceStatusResponse> getInvoices() {
        return invoices;
    }

    /**
     * Lista pobranych faktur.
     */
    public void setInvoices(List<SessionInvoiceStatusResponse> invoices) {
        this.invoices = invoices;
    }
}

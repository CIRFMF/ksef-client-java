package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * ExportInvoicesResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InitAsyncInvoicesQueryResponse {

    /**
     * Numer referencyjny eksportu faktur.
     */
    private String referenceNumber;

    public InitAsyncInvoicesQueryResponse() {
    }

    /**
     * Numer referencyjny eksportu faktur.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny eksportu faktur.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}

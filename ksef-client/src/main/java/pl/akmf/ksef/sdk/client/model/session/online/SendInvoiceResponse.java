package pl.akmf.ksef.sdk.client.model.session.online;

/**
 * SendInvoiceResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SendInvoiceResponse {

    /**
     * Numer referencyjny faktury.
     */
    private String referenceNumber;

    public SendInvoiceResponse() {
    }

    public SendInvoiceResponse(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Numer referencyjny faktury.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny faktury.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}

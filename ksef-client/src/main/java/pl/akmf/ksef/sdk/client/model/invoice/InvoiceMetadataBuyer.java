package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * InvoiceMetadataBuyer.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceMetadataBuyer {

    /**
     * Identyfikator nabywcy.
     */
    private InvoiceBuyerIdentifier identifier;

    /**
     * Nazwa nabywcy.
     */
    private String name;

    public InvoiceMetadataBuyer() {
    }

    /**
     * Identyfikator nabywcy.
     */
    public InvoiceBuyerIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Identyfikator nabywcy.
     */
    public void setIdentifier(InvoiceBuyerIdentifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Nazwa nabywcy.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa nabywcy.
     */
    public void setName(String name) {
        this.name = name;
    }
}

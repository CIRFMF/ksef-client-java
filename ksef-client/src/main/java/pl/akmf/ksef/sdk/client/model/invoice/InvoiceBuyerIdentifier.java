package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * Identyfikator nabywcy.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceQueryBuyerIdentifier, InvoiceMetadataBuyerIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceBuyerIdentifier {

    /**
     * Typ identyfikatora nabywcy.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora nabywcy (exact match).
     */
    private String value;

    public InvoiceBuyerIdentifier() {
    }

    /**
     * Typ identyfikatora nabywcy.
     */
    public IdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora nabywcy.
     */
    public void setType(IdentifierType type) {
        this.type = type;
    }

    /**
     * Wartość identyfikatora nabywcy (exact match).
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora nabywcy (exact match).
     */
    public void setValue(String value) {
        this.value = value;
    }
}

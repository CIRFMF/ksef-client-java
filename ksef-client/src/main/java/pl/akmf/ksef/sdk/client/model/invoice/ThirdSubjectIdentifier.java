package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * Identyfikator podmiotu trzeciego.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceMetadataThirdSubjectIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ThirdSubjectIdentifier {

    /**
     * Typ identyfikatora podmiotu trzeciego.
     */
    private ThirdSubjectIdentifierType type;

    /**
     * Wartość identyfikatora podmiotu trzeciego.
     */
    private String value;

    public ThirdSubjectIdentifier() {
    }

    public ThirdSubjectIdentifier(ThirdSubjectIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Typ identyfikatora podmiotu trzeciego.
     */
    public ThirdSubjectIdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora podmiotu trzeciego.
     */
    public void setType(ThirdSubjectIdentifierType type) {
        this.type = type;
    }

    /**
     * Wartość identyfikatora podmiotu trzeciego.
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora podmiotu trzeciego.
     */
    public void setValue(String value) {
        this.value = value;
    }
}

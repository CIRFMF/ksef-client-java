package pl.akmf.ksef.sdk.client.model.permission.subunit;

/**
 * Identyfikator osoby fizycznej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsSubunitPersonIdentifier {

    /**
     * Typ identyfikatora.
     */
    private PermissionsSubunitIdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public PermissionsSubunitPersonIdentifier() {
    }

    public PermissionsSubunitPersonIdentifier(PermissionsSubunitIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Typ identyfikatora.
     */
    public PermissionsSubunitIdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora.
     */
    public void setType(PermissionsSubunitIdentifierType type) {
        this.type = type;
    }

    /**
     * Wartość identyfikatora.
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora.
     */
    public void setValue(String value) {
        this.value = value;
    }
}

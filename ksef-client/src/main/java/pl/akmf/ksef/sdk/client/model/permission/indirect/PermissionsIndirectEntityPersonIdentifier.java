package pl.akmf.ksef.sdk.client.model.permission.indirect;

/**
 * Identyfikator osoby fizycznej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsIndirectEntityPersonIdentifier {

    /**
     * Typ identyfikatora.
     */
    private PermissionsIndirectEntityIdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public PermissionsIndirectEntityPersonIdentifier() {
    }

    public PermissionsIndirectEntityPersonIdentifier(PermissionsIndirectEntityIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Typ identyfikatora.
     */
    public PermissionsIndirectEntityIdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora.
     */
    public void setType(PermissionsIndirectEntityIdentifierType type) {
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

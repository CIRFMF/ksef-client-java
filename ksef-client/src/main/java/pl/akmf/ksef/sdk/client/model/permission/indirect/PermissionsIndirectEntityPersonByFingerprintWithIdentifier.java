package pl.akmf.ksef.sdk.client.model.permission.indirect;

/**
 * PermissionsIndirectEntityPersonByFingerprintWithIdentifier.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonByFingerprintWithIdentifierDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsIndirectEntityPersonByFingerprintWithIdentifier {

    /**
     * Imię osoby fizycznej.
     */
    private String firstName;

    /**
     * Nazwisko osoby fizycznej.
     */
    private String lastName;

    /**
     * Identyfikator osoby fizycznej.
     */
    private PermissionsIndirectEntityPersonIdentifier identifier;

    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier() {
    }

    public PermissionsIndirectEntityPersonByFingerprintWithIdentifier(String firstName, String lastName, PermissionsIndirectEntityPersonIdentifier identifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifier = identifier;
    }

    /**
     * Imię osoby fizycznej.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Imię osoby fizycznej.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public PermissionsIndirectEntityPersonIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setIdentifier(PermissionsIndirectEntityPersonIdentifier identifier) {
        this.identifier = identifier;
    }
}

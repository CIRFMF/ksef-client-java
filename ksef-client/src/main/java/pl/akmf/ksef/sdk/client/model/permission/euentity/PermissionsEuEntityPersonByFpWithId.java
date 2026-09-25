package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * PermissionsEuEntityPersonByFpWithId.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonByFingerprintWithIdentifierDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PermissionsEuEntityPersonByFpWithId {

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
    private Identifier identifier;

    public PermissionsEuEntityPersonByFpWithId() {
    }

    public PermissionsEuEntityPersonByFpWithId(String firstName, String lastName, Identifier identifier) {
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
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonIdentifier}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public static class Identifier {

        /**
         * Wartość identyfikatora.
         */
        private String value;

        /**
         * Typ identyfikatora.
         */
        private IdentifierType type;

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

        /**
         * Typ identyfikatora.
         */
        public IdentifierType getType() {
            return type;
        }

        /**
         * Typ identyfikatora.
         */
        public void setType(IdentifierType type) {
            this.type = type;
        }
    }

    /**
     * PersonIdentifierType
     * Typ identyfikatora osoby fizycznej.
     * <p>
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Pesel}</li>
     *   <li>{@code Nip}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        Pesel, Nip
    }
}

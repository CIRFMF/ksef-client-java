package pl.akmf.ksef.sdk.client.model.permission.indirect;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator osoby fizycznej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code IndirectPermissionsSubjectIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubjectIdentifier {

    /**
     * Typ identyfikatora.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public SubjectIdentifier(IdentifierType type, String value) {
        this.type = type;
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
     * IndirectPermissionsSubjectIdentifierType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Nip}</li>
     *   <li>{@code Pesel}</li>
     *   <li>{@code Fingerprint}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        NIP("Nip"), PESEL("Pesel"), FINGERPRINT("Fingerprint");

        private final String value;

        IdentifierType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}

package pl.akmf.ksef.sdk.client.model.permission.euentity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator podmiotu uprawnianego.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EuEntityPermissionsSubjectIdentifier, EuEntityAdministrationPermissionsSubjectIdentifier}.
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

    public SubjectIdentifier() {
    }

    public SubjectIdentifier(IdentifierType subjectIdentifierType, String value) {
        this.type = subjectIdentifierType;
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
     * EuEntityPermissionsSubjectIdentifierType, EuEntityAdministrationPermissionsSubjectIdentifierType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Fingerprint}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        FINGERPRINT("Fingerprint");

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

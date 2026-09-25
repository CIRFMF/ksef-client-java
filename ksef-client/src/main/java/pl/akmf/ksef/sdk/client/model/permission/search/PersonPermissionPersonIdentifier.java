package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator osoby fizycznej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionPersonIdentifier {

    /**
     * Typ identyfikatora.
     */
    private PersonPermissionIdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    public String value;

    public PersonPermissionPersonIdentifier() {
    }

    public PersonPermissionPersonIdentifier(PersonPermissionIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Typ identyfikatora.
     */
    public PersonPermissionIdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora.
     */
    public void setType(PersonPermissionIdentifierType type) {
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
     * Typ identyfikatora osoby fizycznej.
     * <p>
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Pesel}</li>
     *   <li>{@code Nip}</li>
     * </ul>
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonIdentifierType}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum PersonPermissionIdentifierType {

        NIP("Nip"), PESEL("Pesel");

        private final String value;

        PersonPermissionIdentifierType(String value) {
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

        @JsonCreator
        public static PersonPermissionIdentifierType fromValue(String value) {
            for (PersonPermissionIdentifierType b : PersonPermissionIdentifierType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

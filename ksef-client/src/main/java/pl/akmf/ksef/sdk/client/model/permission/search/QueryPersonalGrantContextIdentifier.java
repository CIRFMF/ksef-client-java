package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermissionsContextIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonalGrantContextIdentifier {

    /**
     * Typ identyfikatora.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public QueryPersonalGrantContextIdentifier() {
    }

    public QueryPersonalGrantContextIdentifier(IdentifierType type, String value) {
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
     * PersonalPermissionsContextIdentifierType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Nip}</li>
     *   <li>{@code InternalId}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        NIP("Nip"), INTERNAL_ID("InternalId");

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

        @JsonCreator
        public static IdentifierType fromValue(String value) {
            for (IdentifierType b : IdentifierType.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

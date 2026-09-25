package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator podmiotu uprawnionego, jeżeli jest inny niż identyfikator uwierzytelnionego klienta API.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermissionsAuthorizedIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonalGrantAuthorizedIdentifier {

    /**
     * Typ identyfikatora.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public QueryPersonalGrantAuthorizedIdentifier() {
    }

    public QueryPersonalGrantAuthorizedIdentifier(IdentifierType type, String value) {
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
     * IdentifierType.
     * <p>
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermissionsAuthorizedIdentifierType}.
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

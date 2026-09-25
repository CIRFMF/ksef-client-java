package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionsTargetIdentifier {

    /**
     * Typ identyfikatora.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora. W przypadku typu AllPartners należy pozostawić puste. W pozostałych przypadkach pole jest wymagane.
     */
    private String value;

    public PersonPermissionsTargetIdentifier() {
    }

    public PersonPermissionsTargetIdentifier(IdentifierType type, String value) {
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
     * Wartość identyfikatora. W przypadku typu AllPartners należy pozostawić puste. W pozostałych przypadkach pole jest wymagane.
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora. W przypadku typu AllPartners należy pozostawić puste. W pozostałych przypadkach pole jest wymagane.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * IdentifierType.
     * <p>
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonPermissionsTargetIdentifierType}.
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        ALL_PARTNERS("AllPartners"), INTERNAL_ID("InternalId"), NIP("Nip");

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

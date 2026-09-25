package pl.akmf.ksef.sdk.client.model.permission.indirect;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator kontekstu klienta. Nie przekazanie identyfikatora oznacza, że uprawnienie nadane w sposób pośredni jest typu generalnego.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code IndirectPermissionsTargetIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TargetIdentifier {

    /**
     * Typ identyfikatora.
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora. W przypadku typu AllPartners należy pozostawić puste. W pozostałych przypadkach pole jest wymagane.
     */
    private String value;

    public TargetIdentifier(IdentifierType type, String value) {
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
     * IndirectPermissionsTargetIdentifierType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Nip}</li>
     *   <li>{@code AllPartners}</li>
     *   <li>{@code InternalId}</li>
     * </ul>
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
    }
}

package pl.akmf.ksef.sdk.client.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Identyfikator kontekstu do którego następuje uwierzytelnienie.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AuthenticationContextIdentifier, TokenContextIdentifierTypeIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ContextIdentifier {

    /**
     * Typ identyfikatora
     */
    private IdentifierType type;

    /**
     * Wartość identyfikatora
     */
    private String value;

    public ContextIdentifier() {
    }

    public ContextIdentifier(IdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Typ identyfikatora
     */
    public IdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora
     */
    public void setType(IdentifierType type) {
        this.type = type;
    }

    /**
     * Wartość identyfikatora
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * IdentifierType.
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum IdentifierType {

        // nie wystepuje w TokenContextIdentifierType i AuthenticationContextIdentifierType
        ALLPARTNERS("AllPartners"),
        NIP("Nip"),
        INTERNALID("InternalId"),
        NIPVATUE("NipVatUe"),
        // nie wystepuje w EuEntityAdministrationPermissionsContextIdentifierType
        PEPPOLID("PeppolId");

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
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

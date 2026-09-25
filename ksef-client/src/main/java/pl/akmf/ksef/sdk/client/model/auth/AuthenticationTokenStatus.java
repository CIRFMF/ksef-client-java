package pl.akmf.ksef.sdk.client.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AuthenticationTokenStatus.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Pending}</li>
 *   <li>{@code Active}</li>
 *   <li>{@code Revoking}</li>
 *   <li>{@code Revoked}</li>
 *   <li>{@code Failed}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum AuthenticationTokenStatus {

    PENDING("Pending"), ACTIVE("Active"), REVOKING("Revoking"), REVOKED("Revoked"), FAILED("Failed");

    private final String value;

    AuthenticationTokenStatus(String value) {
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
    public static AuthenticationTokenStatus fromValue(String value) {
        for (AuthenticationTokenStatus b : AuthenticationTokenStatus.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

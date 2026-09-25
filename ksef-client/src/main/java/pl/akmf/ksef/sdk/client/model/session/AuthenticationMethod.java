package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Metoda uwierzytelnienia.
 * <p>
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Token}</li>
 *   <li>{@code TrustedProfile}</li>
 *   <li>{@code InternalCertificate}</li>
 *   <li>{@code QualifiedSignature}</li>
 *   <li>{@code QualifiedSeal}</li>
 *   <li>{@code PersonalSignature}</li>
 *   <li>{@code PeppolSignature}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum AuthenticationMethod {

    TOKEN("Token"),
    TRUSTEDPROFILE("TrustedProfile"),
    INTERNALCERTIFICATE("InternalCertificate"),
    QUALIFIEDSIGNATURE("QualifiedSignature"),
    QUALIFIEDSEAL("QualifiedSeal"),
    PERSONALSIGNATURE("PersonalSignature"),
    PEPPOL_SIGNATURE("PeppolSignature");

    private final String value;

    AuthenticationMethod(String value) {
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
    public static AuthenticationMethod fromValue(String value) {
        for (AuthenticationMethod b : AuthenticationMethod.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

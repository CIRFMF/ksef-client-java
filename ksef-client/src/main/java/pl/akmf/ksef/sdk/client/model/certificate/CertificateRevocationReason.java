package pl.akmf.ksef.sdk.client.model.certificate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CertificateRevocationReason.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Unspecified}</li>
 *   <li>{@code Superseded}</li>
 *   <li>{@code KeyCompromise}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum CertificateRevocationReason {

    UNSPECIFIED("Unspecified"), SUPERSEDED("Superseded"), KEYCOMPROMISE("KeyCompromise");

    private final String value;

    CertificateRevocationReason(String value) {
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
    public static CertificateRevocationReason fromValue(String value) {
        for (CertificateRevocationReason b : CertificateRevocationReason.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

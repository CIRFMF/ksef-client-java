package pl.akmf.ksef.sdk.client.model.certificate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CertificateType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Authentication}</li>
 *   <li>{@code Offline}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code KsefCertificateType}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum CertificateType {

    AUTHENTICATION("Authentication"), OFFLINE("Offline");

    private final String value;

    CertificateType(String value) {
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
    public static CertificateType fromValue(String value) {
        for (CertificateType b : CertificateType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

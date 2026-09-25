package pl.akmf.ksef.sdk.client.model.certificate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CertificateSubjectIdentifierType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Nip}</li>
 *   <li>{@code Pesel}</li>
 *   <li>{@code Fingerprint}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum SubjectCertificateIdentifierType {

    FINGERPRINT("Fingerprint"), PESEL("Pesel"), NIP("Nip");

    private final String value;

    SubjectCertificateIdentifierType(String value) {
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
    public static SubjectCertificateIdentifierType fromValue(String value) {
        for (SubjectCertificateIdentifierType b : SubjectCertificateIdentifierType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

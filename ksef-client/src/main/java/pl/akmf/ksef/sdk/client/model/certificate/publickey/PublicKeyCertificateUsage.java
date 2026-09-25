package pl.akmf.ksef.sdk.client.model.certificate.publickey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * PublicKeyCertificateUsage.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code KsefTokenEncryption}</li>
 *   <li>{@code SymmetricKeyEncryption}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum PublicKeyCertificateUsage {

    KSEFTOKENENCRYPTION("KsefTokenEncryption"), SYMMETRICKEYENCRYPTION("SymmetricKeyEncryption");

    private final String value;

    PublicKeyCertificateUsage(String value) {
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
    public static PublicKeyCertificateUsage fromValue(String value) {
        for (PublicKeyCertificateUsage b : PublicKeyCertificateUsage.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

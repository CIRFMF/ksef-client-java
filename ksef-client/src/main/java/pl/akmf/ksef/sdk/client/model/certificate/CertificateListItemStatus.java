package pl.akmf.ksef.sdk.client.model.certificate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CertificateListItemStatus.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Active}</li>
 *   <li>{@code Blocked}</li>
 *   <li>{@code Revoked}</li>
 *   <li>{@code Expired}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum CertificateListItemStatus {

    ACTIVE("Active"), BLOCKED("Blocked"), REVOKED("Revoked"), EXPIRED("Expired");

    private final String value;

    CertificateListItemStatus(String value) {
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
    public static CertificateListItemStatus fromValue(String value) {
        for (CertificateListItemStatus b : CertificateListItemStatus.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

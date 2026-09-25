package pl.akmf.ksef.sdk.client.model.qrcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Typ identyfikatora kontekstu używany lokalnie przez SDK przy generowaniu kodów QR
 * (zgodnie z zasadami znakowania faktur kodem QR).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum ContextIdentifierType {

    NIP("Nip"), INTERNALID("InternalId"), NIPVATUE("NipVatUe");

    private final String value;

    ContextIdentifierType(String value) {
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
    public static ContextIdentifierType fromValue(String value) {
        for (ContextIdentifierType b : ContextIdentifierType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

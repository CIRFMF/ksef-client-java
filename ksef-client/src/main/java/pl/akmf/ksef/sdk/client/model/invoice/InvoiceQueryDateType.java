package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * InvoiceQueryDateType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Issue}</li>
 *   <li>{@code Invoicing}</li>
 *   <li>{@code PermanentStorage}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum InvoiceQueryDateType {

    ISSUE("Issue"), INVOICING("Invoicing"), PERMANENTSTORAGE("PermanentStorage");

    private final String value;

    InvoiceQueryDateType(String value) {
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
    public static InvoiceQueryDateType fromValue(String value) {
        for (InvoiceQueryDateType b : InvoiceQueryDateType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * InvoiceFormType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code FA}</li>
 *   <li>{@code PEF}</li>
 *   <li>{@code FA_RR}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceQueryFormType}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum InvoiceFormType {

    FA("FA"), PEF("PEF"), FA_RR("FA_RR");

    private final String value;

    InvoiceFormType(String value) {
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
    public static InvoiceFormType fromValue(String value) {
        for (InvoiceFormType b : InvoiceFormType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

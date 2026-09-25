package pl.akmf.ksef.sdk.client.model.permission.indirect;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * IndirectPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code InvoiceRead}</li>
 *   <li>{@code InvoiceWrite}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum IndirectPermissionType {

    INVOICE_WRITE("InvoiceWrite"), INVOICE_READ("InvoiceRead");

    private final String value;

    IndirectPermissionType(String value) {
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
}

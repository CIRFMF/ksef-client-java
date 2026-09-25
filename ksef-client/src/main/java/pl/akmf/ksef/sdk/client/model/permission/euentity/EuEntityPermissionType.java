package pl.akmf.ksef.sdk.client.model.permission.euentity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * EuEntityPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code InvoiceWrite}</li>
 *   <li>{@code InvoiceRead}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum EuEntityPermissionType {

    INVOICE_WRITE("InvoiceWrite"), INVOICE_READ("InvoiceRead");

    private final String value;

    EuEntityPermissionType(String value) {
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

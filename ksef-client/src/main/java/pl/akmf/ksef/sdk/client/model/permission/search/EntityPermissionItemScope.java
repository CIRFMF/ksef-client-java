package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * EntityPermissionItemScope.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code InvoiceWrite}</li>
 *   <li>{@code InvoiceRead}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum EntityPermissionItemScope {

    INVOICEWRITE("InvoiceWrite"), INVOICEREAD("InvoiceRead");

    private final String value;

    EntityPermissionItemScope(String value) {
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
    public static EntityPermissionItemScope fromValue(String value) {
        for (EntityPermissionItemScope b : EntityPermissionItemScope.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

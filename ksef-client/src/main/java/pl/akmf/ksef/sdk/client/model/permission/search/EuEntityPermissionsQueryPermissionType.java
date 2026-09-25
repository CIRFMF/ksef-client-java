package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * EuEntityPermissionsQueryPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code VatUeManage}</li>
 *   <li>{@code InvoiceWrite}</li>
 *   <li>{@code InvoiceRead}</li>
 *   <li>{@code Introspection}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum EuEntityPermissionsQueryPermissionType {

    VATUEMANAGE("VatUeManage"), INVOICEWRITE("InvoiceWrite"), INVOICEREAD("InvoiceRead"), INTROSPECTION("Introspection");

    private final String value;

    EuEntityPermissionsQueryPermissionType(String value) {
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
    public static EuEntityPermissionsQueryPermissionType fromValue(String value) {
        for (EuEntityPermissionsQueryPermissionType b : EuEntityPermissionsQueryPermissionType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * InvoicePermissionType, EntityAuthorizationPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code SelfInvoicing}</li>
 *   <li>{@code TaxRepresentative}</li>
 *   <li>{@code RRInvoicing}</li>
 *   <li>{@code PefInvoicing}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum InvoicePermissionType {

    PEF_INVOICING("PefInvoicing"), SELF_INVOICING("SelfInvoicing"), TAX_REPRESENTATIVE("TaxRepresentative"), RR_INVOICING("RRInvoicing");

    private final String value;

    InvoicePermissionType(String value) {
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
    public static InvoicePermissionType fromValue(String value) {
        for (InvoicePermissionType b : InvoicePermissionType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

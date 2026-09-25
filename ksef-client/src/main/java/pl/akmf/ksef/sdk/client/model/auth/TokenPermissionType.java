package pl.akmf.ksef.sdk.client.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * TokenPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code InvoiceRead}</li>
 *   <li>{@code InvoiceWrite}</li>
 *   <li>{@code CredentialsRead}</li>
 *   <li>{@code CredentialsManage}</li>
 *   <li>{@code SubunitManage}</li>
 *   <li>{@code EnforcementOperations}</li>
 *   <li>{@code Introspection}</li>
 *   <li>{@code CollectiveIdentifierManage}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum TokenPermissionType {

    INVOICE_READ("InvoiceRead"),
    INVOICE_WRITE("InvoiceWrite"),
    CREDENTIALS_READ("CredentialsRead"),
    CREDENTIALS_MANAGE("CredentialsManage"),
    SUBUNIT_MANAGE("SubunitManage"),
    ENFORCEMENT_OPERATION("EnforcementOperations"),
    INTROSPECTION("Introspection"),
    COLLECTIVE_IDENTIFIER_MANAGE("CollectiveIdentifierManage");

    private final String value;

    TokenPermissionType(String value) {
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
    public static TokenPermissionType fromValue(String value) {
        for (TokenPermissionType b : TokenPermissionType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

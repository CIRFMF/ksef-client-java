package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * QueryPersonalPermissionTypes.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code CredentialsManage}</li>
 *   <li>{@code CredentialsRead}</li>
 *   <li>{@code InvoiceWrite}</li>
 *   <li>{@code InvoiceRead}</li>
 *   <li>{@code Introspection}</li>
 *   <li>{@code SubunitManage}</li>
 *   <li>{@code EnforcementOperations}</li>
 *   <li>{@code VatUeManage}</li>
 *   <li>{@code CollectiveIdentifierManage}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermissionType, PersonalPermissionScope}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum // PersonalPermission/PersonalPermissionScopeType
QueryPersonalPermissionTypes {

    CREDENTIAL_MANAGE("CredentialsManage"),
    CREDENTIAL_READ("CredentialsRead"),
    INVOICE_WRITE("InvoiceWrite"),
    INVOICE_READ("InvoiceRead"),
    INTROSPECTION("Introspection"),
    SUBUNIT_MANAGE("SubunitManage"),
    ENFORCEMENT_OPERATION("EnforcementOperations"),
    VAT_UE_MANAGE("VatUeManage"),
    COLLECTIVE_IDENTIFIER_MANAGE("CollectiveIdentifierManage");

    private final String value;

    QueryPersonalPermissionTypes(String value) {
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
    public static QueryPersonalPermissionTypes fromValue(String value) {
        for (QueryPersonalPermissionTypes b : QueryPersonalPermissionTypes.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

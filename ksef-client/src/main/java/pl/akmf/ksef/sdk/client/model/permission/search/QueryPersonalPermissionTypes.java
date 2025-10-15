package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QueryPersonalPermissionTypes {

    CREDENTIAL_MANAGE("CredentialsManage"),
    CREDENTIAL_READ("CredentialsRead"),
    INVOICE_WRITE("InvoiceWrite"),
    INVOICE_READ("InvoiceRead"),
    INTROSPECTION("Introspection"),
    SUBUNIT_MANAGE("SubunitManage"),
    ENFORCEMENT_OPERATION("EnforcementOperations"),
    VAT_UE_MANAGE("VatUeManage");

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

package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EuEntityPermissionsQueryPermissionType {

    VATUEMANAGE("VatUeManage"),

    INVOICEWRITE("InvoiceWrite"),

    INVOICEREAD("InvoiceRead"),

    INTROSPECTION("Introspection");

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


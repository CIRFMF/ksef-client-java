package pl.akmf.ksef.sdk.client.model.permission.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets PersonPermissionType
 */
public enum PersonPermissionType {

    CREDENTIALSMANAGE("CredentialsManage"),

    CREDENTIALSREAD("CredentialsRead"),

    INVOICEWRITE("InvoiceWrite"),

    INVOICEREAD("InvoiceRead"),

    INTROSPECTION("Introspection"),

    SUBUNITMANAGE("SubunitManage"),

    ENFORCEMENTOPERATIONS("EnforcementOperations");

    private final String value;

    PersonPermissionType(String value) {
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
    public static PersonPermissionType fromValue(String value) {
        for (PersonPermissionType b : PersonPermissionType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}


package pl.akmf.ksef.sdk.client.model.testdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class TestDataPermission {
    private String description;
    private PermissionType permission;

    public TestDataPermission(String description, PermissionType permission) {
        this.description = description;
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public PermissionType getPermission() {
        return permission;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermission(PermissionType permission) {
        this.permission = permission;
    }

    public enum PermissionType {

        INVOICE_READ("InvoiceRead"),
        INVOICE_WRITE("InvoiceWrite"),
        INTROSPECTION("Introspection"),
        CREDENTIAL_READ("CredentialsRead"),
        CREDENTIAL_MANAGE("CredentialsManage"),
        ENFORCEMENT_OPERATION("EnforcementOperations"),
        SUBUNIT_MANAGE("SubunitManage");

        private final String value;

        PermissionType(String value) {
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
        public static PermissionType fromValue(String value) {
            for (PermissionType b : PermissionType.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

package pl.akmf.ksef.sdk.client.model.permission.indirect;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IndirectPermissionType {

    INVOICE_WRITE("InvoiceWrite"),

    INVOICE_READ("InvoiceRead");

    private final String value;

    IndirectPermissionType(String value) {
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


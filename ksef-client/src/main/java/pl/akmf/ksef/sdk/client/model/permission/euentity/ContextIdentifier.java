package pl.akmf.ksef.sdk.client.model.permission.euentity;

import com.fasterxml.jackson.annotation.JsonValue;

public class ContextIdentifier {
    private IdentifierType type;
    private String value;

    public ContextIdentifier() {
    }

    public ContextIdentifier(IdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum IdentifierType {
        NIP_VAT_UE("NipVatUe");

        private final String value;

        IdentifierType(String value) {
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

}

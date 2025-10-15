package pl.akmf.ksef.sdk.client.model.permission.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public class SubjectIdentifier {
    private IdentifierType type;
    private String value;

    public SubjectIdentifier() {
    }

    public SubjectIdentifier(IdentifierType type, String value) {
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
        NIP("Nip");

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

package pl.akmf.ksef.sdk.client.model.testdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class TestDataContextIdentifier {
    private ContextIdentifierType type;
    private String value;

    public TestDataContextIdentifier(ContextIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContextIdentifierType getType() {
        return type;
    }

    public void setType(ContextIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum ContextIdentifierType {

        NIP("Nip");

        private final String value;

        ContextIdentifierType(String value) {
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
        public static ContextIdentifierType fromValue(String value) {
            for (ContextIdentifierType b : ContextIdentifierType.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

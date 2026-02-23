package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class PersonPermissionPersonIdentifier {
    private PersonPermissionIdentifierType type;
    public String value;

    public PersonPermissionPersonIdentifier() {
    }

    public PersonPermissionPersonIdentifier(PersonPermissionIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public PersonPermissionIdentifierType getType() {
        return type;
    }

    public void setType(PersonPermissionIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum PersonPermissionIdentifierType {

        NIP("Nip"),
        PESEL("Pesel");

        private final String value;

        PersonPermissionIdentifierType(String value) {
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
        public static PersonPermissionIdentifierType fromValue(String value) {
            for (PersonPermissionIdentifierType b : PersonPermissionIdentifierType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

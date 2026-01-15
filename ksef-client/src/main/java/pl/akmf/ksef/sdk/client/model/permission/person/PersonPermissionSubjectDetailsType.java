package pl.akmf.ksef.sdk.client.model.permission.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PersonPermissionSubjectDetailsType {

    PERSON_BY_IDENTIFIER("PersonByIdentifier"),
    PERSON_BY_FINGERPRINT_WITH_IDENTIFIER("PersonByFingerprintWithIdentifier"),
    PERSON_BY_FINGERPRINT_WITHOUT_IDENTIFIER("PersonByFingerprintWithoutIdentifier");

    private final String value;

    PersonPermissionSubjectDetailsType(String value) {
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
    public static PersonPermissionSubjectDetailsType fromValue(String value) {
        for (PersonPermissionSubjectDetailsType b : PersonPermissionSubjectDetailsType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

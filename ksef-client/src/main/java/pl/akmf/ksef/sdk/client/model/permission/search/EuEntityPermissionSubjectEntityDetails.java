package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EuEntityPermissionSubjectEntityDetails {
    private SubjectDetailsType subjectDetailsType;
    private String firstName;
    private String address;

    public EuEntityPermissionSubjectEntityDetails() {

    }

    public SubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(SubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public enum SubjectDetailsType {

        ENTITY_BY_FINGERPRINT("EntityByFingerprint");

        private final String value;

        SubjectDetailsType(String value) {
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
        public static SubjectDetailsType fromValue(String value) {
            for (SubjectDetailsType b : SubjectDetailsType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}
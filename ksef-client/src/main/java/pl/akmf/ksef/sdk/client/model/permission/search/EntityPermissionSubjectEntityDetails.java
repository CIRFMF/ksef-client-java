package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EntityPermissionSubjectEntityDetails {
    private EntityPermissionSubjectDetailsType subjectDetailsType;
    private String fullName;
    private String address;

    public EntityPermissionSubjectEntityDetails() {
    }

    public EntityPermissionSubjectEntityDetails(EntityPermissionSubjectDetailsType subjectDetailsType, String fullName, String address) {
        this.subjectDetailsType = subjectDetailsType;
        this.fullName = fullName;
        this.address = address;
    }

    public EntityPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(EntityPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public enum EntityPermissionSubjectDetailsType {

        ENTITY_BY_IDENTIFIER("EntityByIdentifier"),
        ENTITY_BY_FINGERPRINT("EntityByFingerprint");

        private final String value;

        EntityPermissionSubjectDetailsType(String value) {
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
        public static EntityPermissionSubjectDetailsType fromValue(String value) {
            for (EntityPermissionSubjectDetailsType b : EntityPermissionSubjectDetailsType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EuAdministrationSubjectEntityDetails {
    private String fullName;
    private SubjectDetailType subjectDetailType;

    public EuAdministrationSubjectEntityDetails() {

    }

    public EuAdministrationSubjectEntityDetails(final String fullName, final SubjectDetailType subjectDetailType) {
        this.fullName = fullName;
        this.subjectDetailType = subjectDetailType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public SubjectDetailType getSubjectDetailType() {
        return subjectDetailType;
    }

    public void setSubjectDetailType(SubjectDetailType subjectDetailType) {
        this.subjectDetailType = subjectDetailType;
    }

    public enum SubjectDetailType {

        ENTITY_BY_IDENTIFIER("EntityByIdentifier");

        private final String value;

        SubjectDetailType(String value) {
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
        public static SubjectDetailType fromValue(String value) {
            for (SubjectDetailType b : SubjectDetailType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

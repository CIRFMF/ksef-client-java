package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * PermissionsSubjectEntityByIdentifierDetails.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EuAdministrationSubjectEntityDetails {

    /**
     * Pełna nazwa podmiotu.
     */
    private String fullName;

    private SubjectDetailType subjectDetailType;

    public EuAdministrationSubjectEntityDetails() {
    }

    public EuAdministrationSubjectEntityDetails(final String fullName, final SubjectDetailType subjectDetailType) {
        this.fullName = fullName;
        this.subjectDetailType = subjectDetailType;
    }

    /**
     * Pełna nazwa podmiotu.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Pełna nazwa podmiotu.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public SubjectDetailType getSubjectDetailType() {
        return subjectDetailType;
    }

    public void setSubjectDetailType(SubjectDetailType subjectDetailType) {
        this.subjectDetailType = subjectDetailType;
    }

    /**
     * EntitySubjectByIdentifierDetailsType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code EntityByIdentifier}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
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

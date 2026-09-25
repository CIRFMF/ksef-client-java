package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Dane podmiotu uprawnionego.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsSubjectEntityByFingerprintDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EuEntityPermissionSubjectEntityDetails {

    /**
     * Typ danych podmiotu uprawnionego.
     */
    private SubjectDetailsType subjectDetailsType;

    private String firstName;

    /**
     * Adres podmiotu.
     */
    private String address;

    /**
     * Pełna nazwa podmiotu.
     */
    private String fullName;

    public EuEntityPermissionSubjectEntityDetails() {
    }

    /**
     * Typ danych podmiotu uprawnionego.
     */
    public SubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu uprawnionego.
     */
    public void setSubjectDetailsType(SubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Adres podmiotu.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Adres podmiotu.
     */
    public void setAddress(String address) {
        this.address = address;
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

    /**
     * EntitySubjectByFingerprintDetailsType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code EntityByFingerprint}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
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

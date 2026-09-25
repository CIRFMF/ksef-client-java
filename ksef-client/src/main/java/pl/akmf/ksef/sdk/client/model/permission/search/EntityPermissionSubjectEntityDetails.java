package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Dane podmiotu uprawnionego.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsSubjectEntityByIdentifierDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityPermissionSubjectEntityDetails {

    /**
     * Typ danych podmiotu uprawnionego.
     */
    private EntityPermissionSubjectDetailsType subjectDetailsType;

    /**
     * Pełna nazwa podmiotu.
     */
    private String fullName;

    private String address;

    public EntityPermissionSubjectEntityDetails() {
    }

    public EntityPermissionSubjectEntityDetails(EntityPermissionSubjectDetailsType subjectDetailsType, String fullName, String address) {
        this.subjectDetailsType = subjectDetailsType;
        this.fullName = fullName;
        this.address = address;
    }

    /**
     * Typ danych podmiotu uprawnionego.
     */
    public EntityPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych podmiotu uprawnionego.
     */
    public void setSubjectDetailsType(EntityPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * EntityPermissionSubjectDetailsType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code EntityByIdentifier}</li>
     *   <li>{@code EntityByFingerprint}</li>
     * </ul>
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EntitySubjectDetailsType, EntitySubjectByIdentifierDetailsType}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum EntityPermissionSubjectDetailsType {

        ENTITY_BY_IDENTIFIER("EntityByIdentifier"),
        // w EntitySubjectByIdentifierDetailsType tego nie ma
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

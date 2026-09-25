package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SubunitPermissionType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code CredentialsManage}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SubunitPermissionScope}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum SubunitPermissionType {

    CREDENTIALS_MANAGE("CredentialsManage");

    private final String value;

    SubunitPermissionType(String value) {
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
    public static SubunitPermissionType fromValue(String value) {
        for (SubunitPermissionType b : SubunitPermissionType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * PersonPermissionQueryType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code PermissionsInCurrentContext}</li>
 *   <li>{@code PermissionsGrantedInCurrentContext}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonPermissionsQueryType}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum PersonPermissionQueryType {

    PERMISSION_IN_CURRENT_CONTEXT("PermissionsInCurrentContext"), PERMISSION_GRANTED_IN_CURRENT_CONTEXT("PermissionsGrantedInCurrentContext");

    private final String value;

    PersonPermissionQueryType(String value) {
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
    public static PersonPermissionQueryType fromValue(String value) {
        for (PersonPermissionQueryType b : PersonPermissionQueryType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

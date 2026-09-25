package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * EntityRoleType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code CourtBailiff}</li>
 *   <li>{@code EnforcementAuthority}</li>
 *   <li>{@code LocalGovernmentUnit}</li>
 *   <li>{@code LocalGovernmentSubUnit}</li>
 *   <li>{@code VatGroupUnit}</li>
 *   <li>{@code VatGroupSubUnit}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum EntityRoleType {

    COURT_BAILIFF("CourtBailiff"),
    ENFORCEMENT_AUTHORITY("EnforcementAuthority"),
    LOCAL_GOVERNMENT_UNIT("LocalGovernmentUnit"),
    LOCAL_GOVERNMENT_SUBUNIT("LocalGovernmentSubUnit"),
    VAT_GROUP_UNIT("VatGroupUnit"),
    VAT_GROUP_SUBUNIT("VatGroupSubUnit");

    private final String value;

    EntityRoleType(String value) {
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
    public static EntityRoleType fromValue(String value) {
        for (EntityRoleType b : EntityRoleType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

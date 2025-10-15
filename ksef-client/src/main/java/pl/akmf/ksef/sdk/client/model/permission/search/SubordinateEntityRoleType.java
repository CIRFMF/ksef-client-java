package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SubordinateEntityRoleType {

    LOCAL_GOVERNMENT_SUBUNIT("LocalGovernmentSubUnit"),

    VAT_GROUP_SUBUNIT("VatGroupSubUnit");

    private final String value;

    SubordinateEntityRoleType(String value) {
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
    public static SubordinateEntityRoleType fromValue(String value) {
        for (SubordinateEntityRoleType b : SubordinateEntityRoleType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}


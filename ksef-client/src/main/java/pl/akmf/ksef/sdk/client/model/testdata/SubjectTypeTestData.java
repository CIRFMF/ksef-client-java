package pl.akmf.ksef.sdk.client.model.testdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SubjectTypeTestData {

    ENFORCEMENT_AUTHORITY("EnforcementAuthority"),

    VAT_GROUP("VatGroup"),

    JST("JST");

    private final String value;

    SubjectTypeTestData(String value) {
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
    public static SubjectTypeTestData fromValue(String value) {
        for (SubjectTypeTestData b : SubjectTypeTestData.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}



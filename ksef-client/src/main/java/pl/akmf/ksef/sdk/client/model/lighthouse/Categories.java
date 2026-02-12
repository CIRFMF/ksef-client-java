package pl.akmf.ksef.sdk.client.model.lighthouse;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Categories {
    FAILURE("01"),
    TOTAL_FAILURE("02"),
    PLANNED_UNAVAILABILITY("03");

    private final String value;

    Categories(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * CommonSessionStatus.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code InProgress}</li>
 *   <li>{@code Succeeded}</li>
 *   <li>{@code Failed}</li>
 *   <li>{@code Cancelled}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum CommonSessionStatus {

    SUCCEEDED("Succeeded"), INPROGRESS("InProgress"), FAILED("Failed"), CANCELLED("Cancelled");

    private final String value;

    CommonSessionStatus(String value) {
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
    public static CommonSessionStatus fromValue(String value) {
        for (CommonSessionStatus b : CommonSessionStatus.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

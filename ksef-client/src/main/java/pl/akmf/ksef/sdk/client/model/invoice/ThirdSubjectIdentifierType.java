package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ThirdSubjectIdentifierType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Nip}</li>
 *   <li>{@code InternalId}</li>
 *   <li>{@code VatUe}</li>
 *   <li>{@code Other}</li>
 *   <li>{@code None}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum ThirdSubjectIdentifierType {

    INTERNAL_ID("InternalId"), NONE("None"), OTHER("Other"), NIP("Nip"), VATUE("VatUe");

    private final String value;

    ThirdSubjectIdentifierType(String value) {
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
    public static ThirdSubjectIdentifierType fromValue(String value) {
        for (ThirdSubjectIdentifierType b : ThirdSubjectIdentifierType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

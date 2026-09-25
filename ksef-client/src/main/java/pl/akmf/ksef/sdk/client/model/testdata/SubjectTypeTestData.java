package pl.akmf.ksef.sdk.client.model.testdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SubjectTypeTestData.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code EnforcementAuthority}</li>
 *   <li>{@code VatGroup}</li>
 *   <li>{@code JST}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SubjectType}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum SubjectTypeTestData {

    ENFORCEMENT_AUTHORITY("EnforcementAuthority"), VAT_GROUP("VatGroup"), JST("JST");

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

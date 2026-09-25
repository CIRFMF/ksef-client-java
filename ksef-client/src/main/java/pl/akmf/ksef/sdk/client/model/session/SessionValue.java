package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SessionValue.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code FA}</li>
 *   <li>{@code PEF}</li>
 *   <li>{@code FA_RR}</li>
 * </ul>
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceQueryFormType}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum SessionValue {

    FA("FA"),
    FA_PEF("PEF"),
    @Deprecated
    RR("RR"),
    FA_RR("FA_RR");

    private final String value;

    SessionValue(String value) {
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
    public static SessionValue fromValue(String value) {
        for (SessionValue b : SessionValue.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

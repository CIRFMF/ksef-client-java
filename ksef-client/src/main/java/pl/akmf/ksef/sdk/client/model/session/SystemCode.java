package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Wewnętrzny enum klienta SDK identyfikujący rodzaj/wersję schematu faktury
 * ({@code FA}, {@code PEF}, {@code PEF_KOR}, {@code FA_RR}) używany przy budowaniu
 * nagłówka sesji. Typ techniczny klienta SDK.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum SystemCode {

    FA_2("FA (2)"), PEF_3("PEF (3)"), KOR_PEF_3("PEF_KOR (3)"), FA_3("FA (3)"), FA_RR("FA_RR (1)");

    private final String value;

    SystemCode(String value) {
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
    public static SystemCode fromValue(String value) {
        for (SystemCode b : SystemCode.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

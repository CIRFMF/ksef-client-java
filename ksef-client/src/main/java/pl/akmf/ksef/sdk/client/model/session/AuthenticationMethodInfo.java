package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AuthenticationMethodInfo.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationMethodInfo {

    /**
     * Kategoria metody uwierzytelnienia.
     */
    private AuthenticationMethodInfoCategory category;

    /**
     * Kod metody uwierzytelnienia.
     */
    private String code;

    /**
     * Nazwa metody uwierzytelnienia do wyświetlenia użytkownikowi.
     */
    private String displayName;

    public AuthenticationMethodInfo() {
    }

    public AuthenticationMethodInfo(AuthenticationMethodInfoCategory category, String code, String displayName) {
        this.category = category;
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * Kategoria metody uwierzytelnienia.
     */
    public AuthenticationMethodInfoCategory getCategory() {
        return category;
    }

    /**
     * Kategoria metody uwierzytelnienia.
     */
    public void setCategory(AuthenticationMethodInfoCategory category) {
        this.category = category;
    }

    /**
     * Kod metody uwierzytelnienia.
     */
    public String getCode() {
        return code;
    }

    /**
     * Kod metody uwierzytelnienia.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Nazwa metody uwierzytelnienia do wyświetlenia użytkownikowi.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Nazwa metody uwierzytelnienia do wyświetlenia użytkownikowi.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * AuthenticationMethodInfoCategory.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code XadesSignature}</li>
     *   <li>{@code NationalNode}</li>
     *   <li>{@code Token}</li>
     *   <li>{@code Other}</li>
     * </ul>
     *
     * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AuthenticationMethodCategory}.
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum AuthenticationMethodInfoCategory {

        //Uwierzytelnienie podpisem Xades.
        XADES_SIGNATURE("XadesSignature"),
        //Uwierzytelnienie za pomocą Węzła Krajowego (login.gov.pl).
        NATIONAL_NODE("NationalNode"),
        //Uwierzytelnienie tokenem.
        TOKEN("Token"),
        //Uwierzytelnienie inną metodą.
        OTHER("Other");

        private final String value;

        AuthenticationMethodInfoCategory(String value) {
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
        public static AuthenticationMethodInfoCategory fromValue(String value) {
            for (AuthenticationMethodInfoCategory b : AuthenticationMethodInfoCategory.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

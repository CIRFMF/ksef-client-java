package pl.akmf.ksef.sdk.client.model.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AuthenticationMethodInfo {
    private AuthenticationMethodInfoCategory category;
    private String code;
    private String displayName;

    public AuthenticationMethodInfo() {
    }

    public AuthenticationMethodInfo(AuthenticationMethodInfoCategory category, String code, String displayName) {
        this.category = category;
        this.code = code;
        this.displayName = displayName;
    }

    public AuthenticationMethodInfoCategory getCategory() {
        return category;
    }

    public void setCategory(AuthenticationMethodInfoCategory category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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

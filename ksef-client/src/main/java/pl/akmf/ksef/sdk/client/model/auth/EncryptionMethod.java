package pl.akmf.ksef.sdk.client.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Wewnętrzny enum klienta SDK określający algorytm klucza używany po stronie klienta
 * przy generowaniu par kluczy/certyfikatów ({@code RSA} albo {@code ECDSA}).
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum EncryptionMethod {

    RSA("RSA"), ECDSA("ECDSA");

    private final String value;

    EncryptionMethod(String value) {
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
    public static EncryptionMethod fromValue(String value) {
        for (EncryptionMethod b : EncryptionMethod.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unsupported key algorithm:" + value + "'");
    }
}

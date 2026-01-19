package pl.akmf.ksef.sdk.client.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EncryptionMethod {
    RSA("RSA"),
    ECDSA("ECDSA");

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
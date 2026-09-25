package pl.akmf.ksef.sdk.client.model.testdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * TestDataAuthorizedIdentifier.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataAuthorizedIdentifier {

    private TestDataAuthorizedIdentifierType type;

    private String value;

    public TestDataAuthorizedIdentifier(TestDataAuthorizedIdentifierType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TestDataAuthorizedIdentifierType getType() {
        return type;
    }

    public void setType(TestDataAuthorizedIdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * TestDataAuthorizedIdentifierType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code Nip}</li>
     *   <li>{@code Pesel}</li>
     *   <li>{@code Fingerprint}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum TestDataAuthorizedIdentifierType {

        NIP("Nip"), PESEL("Pesel"), FINGERPRINT("Fingerprint");

        private final String value;

        TestDataAuthorizedIdentifierType(String value) {
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
        public static TestDataAuthorizedIdentifierType fromValue(String value) {
            for (TestDataAuthorizedIdentifierType b : TestDataAuthorizedIdentifierType.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

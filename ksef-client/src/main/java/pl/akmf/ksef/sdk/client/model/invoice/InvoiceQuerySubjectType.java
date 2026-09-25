package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * InvoiceQuerySubjectType.
 * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
 * <ul>
 *   <li>{@code Subject1}</li>
 *   <li>{@code Subject2}</li>
 *   <li>{@code Subject3}</li>
 *   <li>{@code SubjectAuthorized}</li>
 * </ul>
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public enum InvoiceQuerySubjectType {

    SUBJECT1("Subject1"), SUBJECT2("Subject2"), SUBJECT3("Subject3"), SUBJECTAUTHORIZED("SubjectAuthorized");

    private final String value;

    InvoiceQuerySubjectType(String value) {
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
    public static InvoiceQuerySubjectType fromValue(String value) {
        for (InvoiceQuerySubjectType b : InvoiceQuerySubjectType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

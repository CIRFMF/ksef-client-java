package pl.akmf.ksef.sdk.client.model.permission.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;

/**
 * Dane osoby uprawnionej.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PermissionsSubjectPersonDetails}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubunitPermissionSubjectPersonDetails {

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    private SubjectDetailsType subjectDetailsType;

    /**
     * Imię osoby fizycznej.
     */
    private String firstName;

    /**
     * Nazwisko osoby fizycznej.
     */
    private String lastName;

    /**
     * Identyfikator osoby fizycznej.
     */
    private PersonIdentifier personIdentifier;

    /**
     * Data urodzenia osoby fizycznej.
     */
    private LocalDate birthDate;

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    private IdDocument idDocument;

    public SubunitPermissionSubjectPersonDetails() {
    }

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    public SubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    /**
     * Typ danych uprawnionej osoby fizycznej.
     */
    public void setSubjectDetailsType(SubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
    }

    /**
     * Imię osoby fizycznej.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Imię osoby fizycznej.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nazwisko osoby fizycznej.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public PersonIdentifier getPersonIdentifier() {
        return personIdentifier;
    }

    /**
     * Identyfikator osoby fizycznej.
     */
    public void setPersonIdentifier(PersonIdentifier personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Data urodzenia osoby fizycznej.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    public IdDocument getIdDocument() {
        return idDocument;
    }

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     */
    public void setIdDocument(IdDocument idDocument) {
        this.idDocument = idDocument;
    }

    /**
     * PersonSubjectDetailsType.
     * Dozwolone wartości (zgodnie ze specyfikacją OpenAPI KSeF API 2.0):
     * <ul>
     *   <li>{@code PersonByIdentifier}</li>
     *   <li>{@code PersonByFingerprintWithIdentifier}</li>
     *   <li>{@code PersonByFingerprintWithoutIdentifier}</li>
     * </ul>
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum SubjectDetailsType {

        PERSON_BY_IDENTIFIER("PersonByIdentifier"), PERSON_BY_FINGERPRINT_WITH_IDENTIFIER("PersonByFingerprintWithIdentifier"), PERSON_BY_FINGERPRINT_WITHOUT_IDENTIFIER("PersonByFingerprintWithoutIdentifier");

        private final String value;

        SubjectDetailsType(String value) {
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
        public static SubjectDetailsType fromValue(String value) {
            for (SubjectDetailsType b : SubjectDetailsType.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    /**
     * PersonIdentifier.
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public enum PersonIdentifier {

        NIP("Nip"), PESEL("Pesel");

        /**
         * Wartość identyfikatora.
         */
        private final String value;

        PersonIdentifier(String value) {
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
        public static PersonIdentifier fromValue(String value) {
            for (PersonIdentifier b : PersonIdentifier.values()) {
                if (b.value.equalsIgnoreCase(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    /**
     * Dane dokumentu tożsamości osoby fizycznej.
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public static class IdDocument {

        /**
         * Rodzaj dokumentu tożsamości.
         */
        private String type;

        /**
         * Seria i numer dokumentu tożsamości.
         */
        private String number;

        /**
         * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
         */
        private String country;

        public IdDocument() {
        }

        public IdDocument(String type, String number, String country) {
            this.type = type;
            this.number = number;
            this.country = country;
        }

        /**
         * Rodzaj dokumentu tożsamości.
         */
        public String getType() {
            return type;
        }

        /**
         * Rodzaj dokumentu tożsamości.
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Seria i numer dokumentu tożsamości.
         */
        public String getNumber() {
            return number;
        }

        /**
         * Seria i numer dokumentu tożsamości.
         */
        public void setNumber(String number) {
            this.number = number;
        }

        /**
         * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
         */
        public String getCountry() {
            return country;
        }

        /**
         * Kraj wydania dokumentu tożsamości. Musi być zgodny z ISO 3166-1 alpha-2 (np. PL, DE, US) oraz zawierać dokładnie 2 wielkie litery.
         */
        public void setCountry(String country) {
            this.country = country;
        }
    }
}

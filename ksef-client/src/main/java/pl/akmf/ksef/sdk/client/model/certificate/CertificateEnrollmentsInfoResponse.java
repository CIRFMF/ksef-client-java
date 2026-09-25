package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * CertificateEnrollmentsInfoResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code CertificateEnrollmentDataResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateEnrollmentsInfoResponse {

    /**
     * Imię.
     */
    private String givenName;

    /**
     * Nazwisko.
     */
    private String surname;

    /**
     * Nazwa powszechna.
     */
    private String commonName;

    /**
     * Numer seryjny podmiotu.
     */
    private String serialNumber;

    /**
     * Kraj, kod ISO 3166.
     */
    private String countryName;

    /**
     * Unikalny identyfikator.
     */
    private String uniqueIdentifier;

    /**
     * Nazwa organizacji.
     */
    private String organizationName;

    /**
     * Identyfikator organizacji.
     */
    private String organizationIdentifier;

    public CertificateEnrollmentsInfoResponse() {
    }

    /**
     * Imię.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Imię.
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Nazwisko.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Nazwisko.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Nazwa powszechna.
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Nazwa powszechna.
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Numer seryjny podmiotu.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Numer seryjny podmiotu.
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Kraj, kod ISO 3166.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Kraj, kod ISO 3166.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Unikalny identyfikator.
     */
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    /**
     * Unikalny identyfikator.
     */
    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    /**
     * Nazwa organizacji.
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Nazwa organizacji.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * Identyfikator organizacji.
     */
    public String getOrganizationIdentifier() {
        return organizationIdentifier;
    }

    /**
     * Identyfikator organizacji.
     */
    public void setOrganizationIdentifier(String organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
}

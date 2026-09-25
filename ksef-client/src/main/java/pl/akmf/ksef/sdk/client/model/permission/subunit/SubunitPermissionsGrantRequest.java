package pl.akmf.ksef.sdk.client.model.permission.subunit;

/**
 * SubunitPermissionsGrantRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubunitPermissionsGrantRequest {

    /**
     * Identyfikator podmiotu lub osoby fizycznej.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    private ContextIdentifier contextIdentifier;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Nazwa jednostki podrzędnej. W przypadku jednostki podrzędnej z identyfikatorem wewnętrznym pole jest wymagane.
     */
    private String subunitName;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private SubunitSubjectDetails subjectDetails;

    public SubunitPermissionsGrantRequest() {
    }

    public SubunitPermissionsGrantRequest(SubjectIdentifier subjectIdentifier, ContextIdentifier contextIdentifier, String description, String subunitName, SubunitSubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.subunitName = subunitName;
        this.subjectDetails = subjectDetails;
    }

    /**
     * Identyfikator podmiotu lub osoby fizycznej.
     */
    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu lub osoby fizycznej.
     */
    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public void setContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Opis uprawnienia
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis uprawnienia
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Nazwa jednostki podrzędnej. W przypadku jednostki podrzędnej z identyfikatorem wewnętrznym pole jest wymagane.
     */
    public String getSubunitName() {
        return subunitName;
    }

    /**
     * Nazwa jednostki podrzędnej. W przypadku jednostki podrzędnej z identyfikatorem wewnętrznym pole jest wymagane.
     */
    public void setSubunitName(String subunitName) {
        this.subunitName = subunitName;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public SubunitSubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(SubunitSubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }
}

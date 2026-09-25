package pl.akmf.ksef.sdk.client.model.permission.euentity;

/**
 * EuEntityAdministrationPermissionsGrantRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EuEntityPermissionsGrantRequest {

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    private SubjectIdentifier subjectIdentifier;

    /**
     * Identyfikator kontekstu złożonego.
     */
    private ContextIdentifier contextIdentifier;

    /**
     * Opis uprawnienia
     */
    private String description;

    /**
     * Nazwa i adres podmiotu unijnego w formacie:
     * {@code {euSubjectName}, {euSubjectAddress}}
     */
    private String euEntityName;

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    private PermissionsEuEntitySubjectDetails subjectDetails;

    /**
     * Dane podmiotu unijnego, w kontekście którego nadawane są uprawnienia.
     */
    private PermissionsEuEntityDetails euEntityDetails;

    public EuEntityPermissionsGrantRequest() {
    }

    public EuEntityPermissionsGrantRequest(SubjectIdentifier subjectIdentifier, ContextIdentifier contextIdentifier, String description, String euEntityName, PermissionsEuEntitySubjectDetails subjectDetails, PermissionsEuEntityDetails euEntityDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.euEntityName = euEntityName;
        this.subjectDetails = subjectDetails;
        this.euEntityDetails = euEntityDetails;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Identyfikator kontekstu złożonego.
     */
    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu złożonego.
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
     * Nazwa i adres podmiotu unijnego w formacie:
     * {@code {euSubjectName}, {euSubjectAddress}}
     */
    public String getEuEntityName() {
        return euEntityName;
    }

    /**
     * Nazwa i adres podmiotu unijnego w formacie:
     * {@code {euSubjectName}, {euSubjectAddress}}
     */
    public void setEuEntityName(String euEntityName) {
        this.euEntityName = euEntityName;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public PermissionsEuEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    /**
     * Dane podmiotu, któremu nadawane są uprawnienia.
     */
    public void setSubjectDetails(PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    /**
     * Dane podmiotu unijnego, w kontekście którego nadawane są uprawnienia.
     */
    public PermissionsEuEntityDetails getEuEntityDetails() {
        return euEntityDetails;
    }

    /**
     * Dane podmiotu unijnego, w kontekście którego nadawane są uprawnienia.
     */
    public void setEuEntityDetails(PermissionsEuEntityDetails euEntityDetails) {
        this.euEntityDetails = euEntityDetails;
    }
}

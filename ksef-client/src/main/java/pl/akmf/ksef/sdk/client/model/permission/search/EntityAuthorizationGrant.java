package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * EntityAuthorizationGrant.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityAuthorizationGrant {

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Identyfikator osoby nadającej uprawnienie.
     */
    private EntityAuthorizationsAuthorIdentifier authorIdentifier;

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    private EntityAuthorizationsAuthorizedEntityIdentifier authorizedEntityIdentifier;

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    private EntityAuthorizationsAuthorizingEntityIdentifier authorizingEntityIdentifier;

    /**
     * Rodzaj uprawnienia.
     */
    private InvoicePermissionType authorizationScope;

    /**
     * Opis uprawnienia.
     */
    private String description;

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    private OffsetDateTime startDate;

    @Deprecated
    private EuAdministrationSubjectEntityDetails euAdministrationSubjectEntityDetails;

    /**
     * Dane podmiotu uprawnionego.
     */
    private EntityPermissionSubjectEntityDetails subjectEntityDetails;

    public EntityAuthorizationGrant() {
    }

    /**
     * Identyfikator uprawnienia.
     */
    public String getId() {
        return id;
    }

    /**
     * Identyfikator uprawnienia.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Identyfikator osoby nadającej uprawnienie.
     */
    public EntityAuthorizationsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    /**
     * Identyfikator osoby nadającej uprawnienie.
     */
    public void setAuthorIdentifier(EntityAuthorizationsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public EntityAuthorizationsAuthorizedEntityIdentifier getAuthorizedEntityIdentifier() {
        return authorizedEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego.
     */
    public void setAuthorizedEntityIdentifier(EntityAuthorizationsAuthorizedEntityIdentifier authorizedEntityIdentifier) {
        this.authorizedEntityIdentifier = authorizedEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    public EntityAuthorizationsAuthorizingEntityIdentifier getAuthorizingEntityIdentifier() {
        return authorizingEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawniającego.
     */
    public void setAuthorizingEntityIdentifier(EntityAuthorizationsAuthorizingEntityIdentifier authorizingEntityIdentifier) {
        this.authorizingEntityIdentifier = authorizingEntityIdentifier;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public InvoicePermissionType getAuthorizationScope() {
        return authorizationScope;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setAuthorizationScope(InvoicePermissionType authorizationScope) {
        this.authorizationScope = authorizationScope;
    }

    /**
     * Opis uprawnienia.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis uprawnienia.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public EuAdministrationSubjectEntityDetails getEuAdministrationSubjectEntityDetails() {
        return euAdministrationSubjectEntityDetails;
    }

    public void setEuAdministrationSubjectEntityDetails(EuAdministrationSubjectEntityDetails euAdministrationSubjectEntityDetails) {
        this.euAdministrationSubjectEntityDetails = euAdministrationSubjectEntityDetails;
    }

    /**
     * Dane podmiotu uprawnionego.
     */
    public EntityPermissionSubjectEntityDetails getSubjectEntityDetails() {
        return subjectEntityDetails;
    }

    /**
     * Dane podmiotu uprawnionego.
     */
    public void setSubjectEntityDetails(EntityPermissionSubjectEntityDetails subjectEntityDetails) {
        this.subjectEntityDetails = subjectEntityDetails;
    }
}

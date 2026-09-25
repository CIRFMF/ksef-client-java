package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * SubunitPermission.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubunitPermission {

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Identyfikator uprawnionego.
     */
    private SubunitPermissionsAuthorizedIdentifier authorizedIdentifier;

    /**
     * Identyfikator jednostki lub podmiotu podrzędnego.
     */
    private SubunitPermissionsSubunitIdentifier subunitIdentifier;

    /**
     * Identyfikator uprawniającego.
     */
    private SubunitPermissionsAuthorIdentifier authorIdentifier;

    /**
     * Rodzaj uprawnienia.
     */
    private SubunitPermissionType permissionScope;

    /**
     * Opis uprawnienia.
     */
    private String description;

    /**
     * Dane osoby uprawnionej.
     */
    private SubunitPermissionSubjectPersonDetails subjectPersonDetails;

    /**
     * Nazwa jednostki podrzędnej.
     */
    private String subunitName;

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    private OffsetDateTime startDate;

    public SubunitPermission() {
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
     * Identyfikator uprawnionego.
     */
    public SubunitPermissionsAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    /**
     * Identyfikator uprawnionego.
     */
    public void setAuthorizedIdentifier(SubunitPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    /**
     * Identyfikator jednostki lub podmiotu podrzędnego.
     */
    public SubunitPermissionsSubunitIdentifier getSubunitIdentifier() {
        return subunitIdentifier;
    }

    /**
     * Identyfikator jednostki lub podmiotu podrzędnego.
     */
    public void setSubunitIdentifier(SubunitPermissionsSubunitIdentifier subunitIdentifier) {
        this.subunitIdentifier = subunitIdentifier;
    }

    /**
     * Identyfikator uprawniającego.
     */
    public SubunitPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    /**
     * Identyfikator uprawniającego.
     */
    public void setAuthorIdentifier(SubunitPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public SubunitPermissionType getPermissionScope() {
        return permissionScope;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setPermissionScope(SubunitPermissionType permissionScope) {
        this.permissionScope = permissionScope;
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
     * Nazwa jednostki podrzędnej.
     */
    public String getSubunitName() {
        return subunitName;
    }

    /**
     * Nazwa jednostki podrzędnej.
     */
    public void setSubunitName(String subunitName) {
        this.subunitName = subunitName;
    }

    /**
     * Dane osoby uprawnionej.
     */
    public SubunitPermissionSubjectPersonDetails getSubjectPersonDetails() {
        return subjectPersonDetails;
    }

    /**
     * Dane osoby uprawnionej.
     */
    public void setSubjectPersonDetails(SubunitPermissionSubjectPersonDetails subjectPersonDetails) {
        this.subjectPersonDetails = subjectPersonDetails;
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
}

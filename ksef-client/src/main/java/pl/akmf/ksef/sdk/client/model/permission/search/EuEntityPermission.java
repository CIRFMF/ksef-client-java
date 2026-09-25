package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * EuEntityPermission.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EuEntityPermission {

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Identyfikator uprawniającego.
     */
    private EuEntityPermissionsAuthorIdentifier authorIdentifier;

    /**
     * Identyfikator podmiotu unijnego.
     */
    private String vatUeIdentifier;

    /**
     * Nazwa podmiotu unijnego.
     */
    private String euEntityName;

    /**
     * Uprawniony odcisk palca certyfikatu.
     */
    private String authorizedFingerprintIdentifier;

    /**
     * Uprawnienie.
     */
    private EuEntityPermissionsQueryPermissionType permissionScope;

    /**
     * Dane osoby uprawnionej.
     */
    private EuEntityPermissionSubjectPersonDetails subjectPersonDetails;

    /**
     * Dane podmiotu uprawnionego.
     */
    private EuEntityPermissionSubjectEntityDetails subjectEntityDetails;

    /**
     * Dane podmiotu unijnego, w kontekście którego nadane jest uprawnienie.
     */
    private EuEntityPermissionEuEntityDetails euEntityDetails;

    /**
     * Opis uprawnienia.
     */
    private String description;

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    private OffsetDateTime startDate;

    public EuEntityPermission() {
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
     * Identyfikator uprawniającego.
     */
    public EuEntityPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    /**
     * Identyfikator uprawniającego.
     */
    public void setAuthorIdentifier(EuEntityPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    /**
     * Identyfikator podmiotu unijnego.
     */
    public String getVatUeIdentifier() {
        return vatUeIdentifier;
    }

    /**
     * Identyfikator podmiotu unijnego.
     */
    public void setVatUeIdentifier(String vatUeIdentifier) {
        this.vatUeIdentifier = vatUeIdentifier;
    }

    /**
     * Nazwa podmiotu unijnego.
     */
    public String getEuEntityName() {
        return euEntityName;
    }

    /**
     * Nazwa podmiotu unijnego.
     */
    public void setEuEntityName(String euEntityName) {
        this.euEntityName = euEntityName;
    }

    /**
     * Uprawniony odcisk palca certyfikatu.
     */
    public String getAuthorizedFingerprintIdentifier() {
        return authorizedFingerprintIdentifier;
    }

    /**
     * Uprawniony odcisk palca certyfikatu.
     */
    public void setAuthorizedFingerprintIdentifier(String authorizedFingerprintIdentifier) {
        this.authorizedFingerprintIdentifier = authorizedFingerprintIdentifier;
    }

    /**
     * Uprawnienie.
     */
    public EuEntityPermissionsQueryPermissionType getPermissionScope() {
        return permissionScope;
    }

    /**
     * Uprawnienie.
     */
    public void setPermissionScope(EuEntityPermissionsQueryPermissionType permissionScope) {
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
     * Dane osoby uprawnionej.
     */
    public EuEntityPermissionSubjectPersonDetails getSubjectPersonDetails() {
        return subjectPersonDetails;
    }

    /**
     * Dane osoby uprawnionej.
     */
    public void setSubjectPersonDetails(EuEntityPermissionSubjectPersonDetails subjectPersonDetails) {
        this.subjectPersonDetails = subjectPersonDetails;
    }

    /**
     * Dane podmiotu uprawnionego.
     */
    public EuEntityPermissionSubjectEntityDetails getSubjectEntityDetails() {
        return subjectEntityDetails;
    }

    /**
     * Dane podmiotu uprawnionego.
     */
    public void setSubjectEntityDetails(EuEntityPermissionSubjectEntityDetails subjectEntityDetails) {
        this.subjectEntityDetails = subjectEntityDetails;
    }

    /**
     * Dane podmiotu unijnego, w kontekście którego nadane jest uprawnienie.
     */
    public EuEntityPermissionEuEntityDetails getEuEntityDetails() {
        return euEntityDetails;
    }

    /**
     * Dane podmiotu unijnego, w kontekście którego nadane jest uprawnienie.
     */
    public void setEuEntityDetails(EuEntityPermissionEuEntityDetails euEntityDetails) {
        this.euEntityDetails = euEntityDetails;
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

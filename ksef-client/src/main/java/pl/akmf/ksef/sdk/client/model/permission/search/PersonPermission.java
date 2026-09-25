package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import java.time.OffsetDateTime;

/**
 * PersonPermission.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermission {

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Identyfikator osoby lub podmiotu uprawnionego.
     */
    private PersonPermissionsAuthorizedIdentifier authorizedIdentifier;

    /**
     * Identyfikator kontekstu uprawnienia (dla uprawnień nadanych administratorom jednostek podrzędnych).
     */
    private PersonPermissionsContextIdentifier contextIdentifier;

    /**
     * Identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio.
     */
    private PersonPermissionsTargetIdentifier targetIdentifier;

    /**
     * Identyfikator osoby lub podmiotu nadającego uprawnienie.
     */
    private PersonPermissionsAuthorIdentifier authorIdentifier;

    /**
     * Rodzaj uprawnienia.
     */
    private PersonPermissionType permissionScope;

    /**
     * Opis uprawnienia.
     */
    private String description;

    /**
     * Stan uprawnienia.
     */
    private PermissionState permissionState;

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    private OffsetDateTime startDate;

    /**
     * Flaga określająca, czy uprawnienie ma być możliwe do dalszego przekazywania.
     */
    private boolean canDelegate;

    /**
     * Dane osoby uprawnionej.
     */
    private PersonPermissionSubjectPersonDetails subjectPersonDetails;

    /**
     * Dane podmiotu uprawnionego.
     */
    private EntityPermissionSubjectEntityDetails subjectEntityDetails;

    public PersonPermission() {
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
     * Identyfikator osoby lub podmiotu uprawnionego.
     */
    public PersonPermissionsAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    /**
     * Identyfikator osoby lub podmiotu uprawnionego.
     */
    public void setAuthorizedIdentifier(PersonPermissionsAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    /**
     * Identyfikator kontekstu uprawnienia (dla uprawnień nadanych administratorom jednostek podrzędnych).
     */
    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu uprawnienia (dla uprawnień nadanych administratorom jednostek podrzędnych).
     */
    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio.
     */
    public PersonPermissionsTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    /**
     * Identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio.
     */
    public void setTargetIdentifier(PersonPermissionsTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    /**
     * Identyfikator osoby lub podmiotu nadającego uprawnienie.
     */
    public PersonPermissionsAuthorIdentifier getAuthorIdentifier() {
        return authorIdentifier;
    }

    /**
     * Identyfikator osoby lub podmiotu nadającego uprawnienie.
     */
    public void setAuthorIdentifier(PersonPermissionsAuthorIdentifier authorIdentifier) {
        this.authorIdentifier = authorIdentifier;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public PersonPermissionType getPermissionScope() {
        return permissionScope;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setPermissionScope(PersonPermissionType permissionScope) {
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
     * Stan uprawnienia.
     */
    public PermissionState getPermissionState() {
        return permissionState;
    }

    /**
     * Stan uprawnienia.
     */
    public void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
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

    public boolean isCanDelegate() {
        return canDelegate;
    }

    /**
     * Flaga określająca, czy uprawnienie ma być możliwe do dalszego przekazywania.
     */
    public void setCanDelegate(boolean canDelegate) {
        this.canDelegate = canDelegate;
    }

    /**
     * Dane osoby uprawnionej.
     */
    public PersonPermissionSubjectPersonDetails getSubjectPersonDetails() {
        return subjectPersonDetails;
    }

    /**
     * Dane osoby uprawnionej.
     */
    public void setSubjectPersonDetails(PersonPermissionSubjectPersonDetails subjectPersonDetails) {
        this.subjectPersonDetails = subjectPersonDetails;
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

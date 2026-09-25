package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * QueryPersonalGrantItem.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermission}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonalGrantItem {

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    private QueryPersonalGrantContextIdentifier contextIdentifier;

    /**
     * Identyfikator podmiotu uprawnionego, jeżeli jest inny niż identyfikator uwierzytelnionego klienta API.
     */
    private QueryPersonalGrantAuthorizedIdentifier authorizedIdentifier;

    /**
     * Identyfikator podmiotu docelowego dla uprawnień selektywnych nadanych pośrednio.
     */
    private QueryPersonalGrantTargetIdentifier targetIdentifier;

    /**
     * Rodzaj uprawnienia.
     */
    private QueryPersonalPermissionTypes permissionScope;

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
    private Boolean canDelegate;

    /**
     * Dane podmiotu uprawnionego.
     */
    private EntityPermissionSubjectEntityDetails subjectEntityDetails;

    /**
     * Dane osoby uprawnionej.
     */
    private PersonPermissionSubjectPersonDetails subjectPersonDetails;

    public QueryPersonalGrantItem() {
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
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public QueryPersonalGrantContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public void setContextIdentifier(QueryPersonalGrantContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego, jeżeli jest inny niż identyfikator uwierzytelnionego klienta API.
     */
    public QueryPersonalGrantAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    /**
     * Identyfikator podmiotu uprawnionego, jeżeli jest inny niż identyfikator uwierzytelnionego klienta API.
     */
    public void setAuthorizedIdentifier(QueryPersonalGrantAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }

    /**
     * Identyfikator podmiotu docelowego dla uprawnień selektywnych nadanych pośrednio.
     */
    public QueryPersonalGrantTargetIdentifier getTargetIdentifier() {
        return targetIdentifier;
    }

    /**
     * Identyfikator podmiotu docelowego dla uprawnień selektywnych nadanych pośrednio.
     */
    public void setTargetIdentifier(QueryPersonalGrantTargetIdentifier targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public QueryPersonalPermissionTypes getPermissionScope() {
        return permissionScope;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setPermissionScope(QueryPersonalPermissionTypes permissionScope) {
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

    /**
     * Flaga określająca, czy uprawnienie ma być możliwe do dalszego przekazywania.
     */
    public Boolean getCanDelegate() {
        return canDelegate;
    }

    /**
     * Flaga określająca, czy uprawnienie ma być możliwe do dalszego przekazywania.
     */
    public void setCanDelegate(Boolean canDelegate) {
        this.canDelegate = canDelegate;
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
}

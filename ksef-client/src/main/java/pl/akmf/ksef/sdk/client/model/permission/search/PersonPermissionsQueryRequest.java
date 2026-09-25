package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import java.util.List;

/**
 * PersonPermissionsQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PersonPermissionsQueryRequest {

    /**
     * Identyfikator osoby lub podmiotu nadającego uprawnienie.
     */
    private PersonPermissionsAuthorIdentifier authorIdentifier;

    /**
     * Identyfikator osoby lub podmiotu uprawnionego.
     */
    private PersonPermissionsAuthorizedIdentifier authorizedIdentifier;

    /**
     * Identyfikator podmiotu docelowego dla uprawnień nadanych pośrednio.
     */
    private PersonPermissionsTargetIdentifier targetIdentifier;

    /**
     * Identyfikator kontekstu uprawnienia (dla uprawnień nadanych administratorom jednostek podrzędnych).
     */
    private PersonPermissionsContextIdentifier contextIdentifier;

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    private List<PersonPermissionType> permissionTypes;

    /**
     * Stan uprawnienia.
     */
    private PermissionState permissionState;

    /**
     * Typ zapytania.
     */
    private PersonPermissionQueryType queryType;

    public PersonPermissionsQueryRequest() {
    }

    public PersonPermissionsQueryRequest(PersonPermissionsAuthorIdentifier authorIdentifier, PersonPermissionsAuthorizedIdentifier authorizedIdentifier, PersonPermissionsTargetIdentifier targetIdentifier, PersonPermissionsContextIdentifier contextIdentifier, List<PersonPermissionType> permissionTypes, PermissionState permissionState, PersonPermissionQueryType queryType) {
        this.authorIdentifier = authorIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.contextIdentifier = contextIdentifier;
        this.permissionTypes = permissionTypes;
        this.permissionState = permissionState;
        this.queryType = queryType;
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
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public List<PersonPermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public void setPermissionTypes(List<PersonPermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
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
     * Typ zapytania.
     */
    public PersonPermissionQueryType getQueryType() {
        return queryType;
    }

    /**
     * Typ zapytania.
     */
    public void setQueryType(PersonPermissionQueryType queryType) {
        this.queryType = queryType;
    }
}

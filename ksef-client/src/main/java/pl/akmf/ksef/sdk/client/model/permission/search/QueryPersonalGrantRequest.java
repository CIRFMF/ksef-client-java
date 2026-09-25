package pl.akmf.ksef.sdk.client.model.permission.search;

import java.util.List;

/**
 * QueryPersonalGrantRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonalPermissionsQueryRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryPersonalGrantRequest {

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    private QueryPersonalGrantContextIdentifier contextIdentifier;

    /**
     * Identyfikator podmiotu docelowego dla uprawnień selektywnych nadanych pośrednio.
     */
    private QueryPersonalGrantTargetIdentifier targetIdentifier;

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    private List<QueryPersonalPermissionTypes> permissionTypes;

    /**
     * Stan uprawnienia.
     */
    private PermissionState permissionState;

    public QueryPersonalGrantRequest() {
    }

    public QueryPersonalGrantRequest(QueryPersonalGrantContextIdentifier contextIdentifier, QueryPersonalGrantTargetIdentifier targetIdentifier, List<QueryPersonalPermissionTypes> permissionTypes, PermissionState permissionState) {
        this.contextIdentifier = contextIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.permissionTypes = permissionTypes;
        this.permissionState = permissionState;
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
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public List<QueryPersonalPermissionTypes> getPermissionTypes() {
        return permissionTypes;
    }

    /**
     * Lista rodzajów wyszukiwanych uprawnień.
     */
    public void setPermissionTypes(List<QueryPersonalPermissionTypes> permissionTypes) {
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
}

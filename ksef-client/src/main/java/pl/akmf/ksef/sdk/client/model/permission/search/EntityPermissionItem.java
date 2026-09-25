package pl.akmf.ksef.sdk.client.model.permission.search;

import java.time.OffsetDateTime;

/**
 * EntityPermissionItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityPermissionItem {

    /**
     * Flaga określająca, czy uprawnienie ma być możliwe do dalszego przekazywania.
     */
    private Boolean canDelegate;

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    private PersonPermissionsContextIdentifier contextIdentifier;

    /**
     * Opis uprawnienia.
     */
    private String description;

    /**
     * Identyfikator uprawnienia.
     */
    private String id;

    /**
     * Rodzaj uprawnienia.
     */
    private EntityPermissionItemScope permissionScope;

    /**
     * Data rozpoczęcia obowiązywania uprawnienia.
     */
    private OffsetDateTime startDate;

    public EntityPermissionItem() {
    }

    public EntityPermissionItem(Boolean canDelegate, PersonPermissionsContextIdentifier contextIdentifier, String description, String id, EntityPermissionItemScope permissionScope, OffsetDateTime startDate) {
        this.canDelegate = canDelegate;
        this.contextIdentifier = contextIdentifier;
        this.description = description;
        this.id = id;
        this.permissionScope = permissionScope;
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
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public PersonPermissionsContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu podmiotu, który nadał uprawnienia do obsługi faktur.
     */
    public void setContextIdentifier(PersonPermissionsContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
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
     * Rodzaj uprawnienia.
     */
    public EntityPermissionItemScope getPermissionScope() {
        return permissionScope;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setPermissionScope(EntityPermissionItemScope permissionScope) {
        this.permissionScope = permissionScope;
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

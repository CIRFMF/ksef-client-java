package pl.akmf.ksef.sdk.client.model.permission.entity;

/**
 * EntityPermission.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EntityPermission {

    /**
     * Rodzaj uprawnienia.
     */
    private EntityPermissionType type;

    /**
     * Flaga pozwalająca na pośrednie przekazywanie danego uprawnienia
     */
    private Boolean canDelegate;

    public EntityPermission() {
    }

    public EntityPermission(EntityPermissionType type, Boolean canDelegate) {
        this.type = type;
        this.canDelegate = canDelegate;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public EntityPermissionType getType() {
        return type;
    }

    /**
     * Rodzaj uprawnienia.
     */
    public void setType(EntityPermissionType type) {
        this.type = type;
    }

    /**
     * Flaga pozwalająca na pośrednie przekazywanie danego uprawnienia
     */
    public Boolean getCanDelegate() {
        return canDelegate;
    }

    /**
     * Flaga pozwalająca na pośrednie przekazywanie danego uprawnienia
     */
    public void setCanDelegate(Boolean canDelegate) {
        this.canDelegate = canDelegate;
    }
}

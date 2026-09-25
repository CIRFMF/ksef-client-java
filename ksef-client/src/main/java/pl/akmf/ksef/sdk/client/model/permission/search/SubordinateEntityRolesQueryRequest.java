package pl.akmf.ksef.sdk.client.model.permission.search;

/**
 * SubordinateEntityRolesQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubordinateEntityRolesQueryRequest {

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    private EntityPermissionsSubordinateEntityIdentifier subordinateEntityIdentifier;

    public SubordinateEntityRolesQueryRequest() {
    }

    public SubordinateEntityRolesQueryRequest(EntityPermissionsSubordinateEntityIdentifier subordinateEntityIdentifier) {
        this.subordinateEntityIdentifier = subordinateEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public EntityPermissionsSubordinateEntityIdentifier getSubordinateEntityIdentifier() {
        return subordinateEntityIdentifier;
    }

    /**
     * Identyfikator podmiotu podrzędnego.
     */
    public void setSubordinateEntityIdentifier(EntityPermissionsSubordinateEntityIdentifier subordinateEntityIdentifier) {
        this.subordinateEntityIdentifier = subordinateEntityIdentifier;
    }
}

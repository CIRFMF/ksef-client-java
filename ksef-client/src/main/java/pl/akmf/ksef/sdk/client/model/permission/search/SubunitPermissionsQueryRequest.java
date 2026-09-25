package pl.akmf.ksef.sdk.client.model.permission.search;

/**
 * SubunitPermissionsQueryRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubunitPermissionsQueryRequest {

    /**
     * Identyfikator jednostki lub podmiotu podrzędnego.
     */
    private SubunitPermissionsSubunitIdentifier subunitIdentifier;

    public SubunitPermissionsQueryRequest() {
    }

    public SubunitPermissionsQueryRequest(SubunitPermissionsSubunitIdentifier subunitIdentifier) {
        this.subunitIdentifier = subunitIdentifier;
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
}

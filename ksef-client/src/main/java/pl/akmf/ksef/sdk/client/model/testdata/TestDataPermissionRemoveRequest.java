package pl.akmf.ksef.sdk.client.model.testdata;

/**
 * TestDataPermissionRemoveRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code TestDataPermissionsRevokeRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataPermissionRemoveRequest {

    private TestDataContextIdentifier contextIdentifier;

    private TestDataAuthorizedIdentifier authorizedIdentifier;

    public TestDataPermissionRemoveRequest() {
    }

    public TestDataPermissionRemoveRequest(TestDataContextIdentifier contextIdentifier, TestDataAuthorizedIdentifier authorizedIdentifier) {
        this.contextIdentifier = contextIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
    }

    public TestDataContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    public void setContextIdentifier(TestDataContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    public TestDataAuthorizedIdentifier getAuthorizedIdentifier() {
        return authorizedIdentifier;
    }

    public void setAuthorizedIdentifier(TestDataAuthorizedIdentifier authorizedIdentifier) {
        this.authorizedIdentifier = authorizedIdentifier;
    }
}

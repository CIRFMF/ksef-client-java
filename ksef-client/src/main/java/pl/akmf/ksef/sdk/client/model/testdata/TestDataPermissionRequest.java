package pl.akmf.ksef.sdk.client.model.testdata;

import java.util.List;

/**
 * TestDataContextIdentifier.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code TestDataPermissionsGrantRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataPermissionRequest {

    private TestDataContextIdentifier contextIdentifier;

    private TestDataAuthorizedIdentifier authorizedIdentifier;

    private List<TestDataPermission> permissions;

    public TestDataPermissionRequest() {
    }

    public TestDataPermissionRequest(TestDataContextIdentifier contextIdentifier, TestDataAuthorizedIdentifier authorizedIdentifier, List<TestDataPermission> permissions) {
        this.contextIdentifier = contextIdentifier;
        this.authorizedIdentifier = authorizedIdentifier;
        this.permissions = permissions;
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

    public List<TestDataPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<TestDataPermission> permissions) {
        this.permissions = permissions;
    }
}

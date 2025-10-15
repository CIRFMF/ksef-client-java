package pl.akmf.ksef.sdk.client.model.testdata;

import java.util.List;

public class TestDataPermissionRequest {
    private TestDataContextIdentifier contextIdentifier;
    private TestDataAuthorizedIdentifier authorizedIdentifier;
    private List<TestDataPermission> permissions;

    public TestDataPermissionRequest() {

    }

    public TestDataPermissionRequest(TestDataContextIdentifier contextIdentifier,
                                     TestDataAuthorizedIdentifier authorizedIdentifier,
                                     List<TestDataPermission> permissions) {
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

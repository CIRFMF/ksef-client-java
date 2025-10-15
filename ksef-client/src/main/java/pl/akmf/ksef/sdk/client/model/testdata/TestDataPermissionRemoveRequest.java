package pl.akmf.ksef.sdk.client.model.testdata;

public class TestDataPermissionRemoveRequest {
    private TestDataContextIdentifier contextIdentifier;
    private TestDataAuthorizedIdentifier authorizedIdentifier;

    public TestDataPermissionRemoveRequest() {

    }

    public TestDataPermissionRemoveRequest(TestDataContextIdentifier contextIdentifier,TestDataAuthorizedIdentifier authorizedIdentifier) {
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

package pl.akmf.ksef.sdk.client.model.testdata;

/**
 * TestDataAttachmentRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AttachmentPermissionGrantRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataAttachmentRequest {

    private String nip;

    public TestDataAttachmentRequest() {
    }

    public TestDataAttachmentRequest(String nip) {
        this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}

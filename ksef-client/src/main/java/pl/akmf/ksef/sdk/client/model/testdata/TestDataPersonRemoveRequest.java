package pl.akmf.ksef.sdk.client.model.testdata;

/**
 * TestDataPersonRemoveRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code PersonRemoveRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataPersonRemoveRequest {

    private String nip;

    public TestDataPersonRemoveRequest(String nip) {
        this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}

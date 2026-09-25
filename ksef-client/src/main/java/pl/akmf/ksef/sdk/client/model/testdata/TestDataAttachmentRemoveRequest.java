package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.LocalDate;

/**
 * TestDataAttachmentRemoveRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AttachmentPermissionRevokeRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataAttachmentRemoveRequest {

    private String nip;

    /**
     * Data wycofania zgody na przesyłanie faktur z załącznikiem.
     */
    private LocalDate expectedEndDate;

    public TestDataAttachmentRemoveRequest() {
    }

    public TestDataAttachmentRemoveRequest(String nip) {
        this.nip = nip;
    }

    public TestDataAttachmentRemoveRequest(String nip, LocalDate expectedEndDate) {
        this.nip = nip;
        this.expectedEndDate = expectedEndDate;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * Data wycofania zgody na przesyłanie faktur z załącznikiem.
     */
    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    /**
     * Data wycofania zgody na przesyłanie faktur z załącznikiem.
     */
    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }
}

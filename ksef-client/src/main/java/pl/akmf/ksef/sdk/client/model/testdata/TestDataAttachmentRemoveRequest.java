package pl.akmf.ksef.sdk.client.model.testdata;

public class TestDataAttachmentRemoveRequest {
    private String nip;

    public TestDataAttachmentRemoveRequest() {

    }

    public TestDataAttachmentRemoveRequest(String nip) {
        this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}

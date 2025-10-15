package pl.akmf.ksef.sdk.client.model.testdata;

public class TestDataAttachmentRequest {
    private String nip;

    public TestDataAttachmentRequest() {

    }

    public TestDataAttachmentRequest(String nip){
        this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}

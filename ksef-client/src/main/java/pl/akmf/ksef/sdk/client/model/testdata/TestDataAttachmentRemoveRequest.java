package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.LocalDate;

public class TestDataAttachmentRemoveRequest {
    private String nip;
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

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }
}

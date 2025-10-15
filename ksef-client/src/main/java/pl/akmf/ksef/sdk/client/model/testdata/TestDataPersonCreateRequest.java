package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.OffsetDateTime;

public class TestDataPersonCreateRequest {
    private String nip;
    private String pesel;
    private Boolean isBailiff;
    private String description;
    private OffsetDateTime createdDate;

    public TestDataPersonCreateRequest() {

    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Boolean getIsBailiff() {
        return isBailiff;
    }

    public void setIsBailiff(Boolean isBailiff) {
        this.isBailiff = isBailiff;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }
}

package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.OffsetDateTime;

// Aktualizacja danych certyfikatu (tylko na środowiskach testowych)
public class TestDataUpdateCertificateRequest {

    // Nowa data ważności certyfikatu, nie może być późniejsza niż obecna.
    private OffsetDateTime validTo;

    public TestDataUpdateCertificateRequest() {
    }

    public TestDataUpdateCertificateRequest(OffsetDateTime validTo) {
        this.validTo = validTo;
    }

    public OffsetDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
    }
}


package pl.akmf.ksef.sdk.client.model.testdata;

import java.time.OffsetDateTime;

/**
 * TestDataUpdateCertificateRequest.
 *
 * Aktualizacja danych certyfikatu (tylko na środowiskach testowych)
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TestDataUpdateCertificateRequest {

    /**
     * Nowa data ważności certyfikatu, nie może być późniejsza niż obecna.
     */
    private OffsetDateTime validTo;

    public TestDataUpdateCertificateRequest() {
    }

    public TestDataUpdateCertificateRequest(OffsetDateTime validTo) {
        this.validTo = validTo;
    }

    /**
     * Nowa data ważności certyfikatu, nie może być późniejsza niż obecna.
     */
    public OffsetDateTime getValidTo() {
        return validTo;
    }

    /**
     * Nowa data ważności certyfikatu, nie może być późniejsza niż obecna.
     */
    public void setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
    }
}

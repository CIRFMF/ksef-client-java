package pl.akmf.ksef.sdk.client.model.certificate;

import java.util.List;

/**
 * CertificateListRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code RetrieveCertificatesRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateListRequest {

    /**
     * Numery seryjne certyfikatów do pobrania.
     */
    private List<String> certificateSerialNumbers;

    public CertificateListRequest() {
    }

    public CertificateListRequest(List<String> certificateSerialNumbers) {
        this.certificateSerialNumbers = certificateSerialNumbers;
    }

    /**
     * Numery seryjne certyfikatów do pobrania.
     */
    public List<String> getCertificateSerialNumbers() {
        return certificateSerialNumbers;
    }

    /**
     * Numery seryjne certyfikatów do pobrania.
     */
    public void setCertificateSerialNumbers(List<String> certificateSerialNumbers) {
        this.certificateSerialNumbers = certificateSerialNumbers;
    }
}

package pl.akmf.ksef.sdk.client.model.certificate;

import java.util.List;

/**
 * CertificateListResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code RetrieveCertificatesResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateListResponse {

    /**
     * Pobrane certyfikaty.
     */
    private List<RetrieveCertificatesListItem> certificates;

    public CertificateListResponse() {
    }

    public CertificateListResponse(List<RetrieveCertificatesListItem> certificates) {
        this.certificates = certificates;
    }

    /**
     * Pobrane certyfikaty.
     */
    public List<RetrieveCertificatesListItem> getCertificates() {
        return certificates;
    }

    /**
     * Pobrane certyfikaty.
     */
    public void setCertificates(List<RetrieveCertificatesListItem> certificates) {
        this.certificates = certificates;
    }
}

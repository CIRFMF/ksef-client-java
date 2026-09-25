package pl.akmf.ksef.sdk.client.model.certificate;

import java.util.List;

/**
 * CertificateMetadataListResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code QueryCertificatesResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateMetadataListResponse {

    /**
     * Lista certyfikatów spełniających kryteria wyszukiwania.
     */
    private List<CertificateInfo> certificates;

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    private Boolean hasMore;

    public CertificateMetadataListResponse() {
    }

    public CertificateMetadataListResponse(List<CertificateInfo> certificates, Boolean hasMore) {
        this.certificates = certificates;
        this.hasMore = hasMore;
    }

    /**
     * Lista certyfikatów spełniających kryteria wyszukiwania.
     */
    public List<CertificateInfo> getCertificates() {
        return certificates;
    }

    /**
     * Lista certyfikatów spełniających kryteria wyszukiwania.
     */
    public void setCertificates(List<CertificateInfo> certificates) {
        this.certificates = certificates;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     * Flaga informująca o dostępności kolejnej strony wyników.
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}

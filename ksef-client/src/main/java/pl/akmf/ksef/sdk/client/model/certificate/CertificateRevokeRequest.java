package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * CertificateRevokeRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code RevokeCertificateRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateRevokeRequest {

    /**
     * Powód unieważnienia certyfikatu.
     */
    private CertificateRevocationReason revocationReason;

    public CertificateRevokeRequest() {
    }

    public CertificateRevokeRequest(CertificateRevocationReason revocationReason) {
        this.revocationReason = revocationReason;
    }

    /**
     * Powód unieważnienia certyfikatu.
     */
    public CertificateRevocationReason getRevocationReason() {
        return revocationReason;
    }

    /**
     * Powód unieważnienia certyfikatu.
     */
    public void setRevocationReason(CertificateRevocationReason revocationReason) {
        this.revocationReason = revocationReason;
    }
}

package pl.akmf.ksef.sdk.client.model.limit;

/**
 * CertificateEffectiveSubjectLimits, CertificateSubjectLimitsOverride.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateLimit {

    private int maxCertificates;

    public CertificateLimit() {
    }

    public CertificateLimit(int maxCertificates) {
        this.maxCertificates = maxCertificates;
    }

    public int getMaxCertificates() {
        return maxCertificates;
    }

    public void setMaxCertificates(int maxCertificates) {
        this.maxCertificates = maxCertificates;
    }
}

package pl.akmf.ksef.sdk.client.model.limit;

/**
 * GetSubjectLimitResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveSubjectLimits}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GetSubjectLimitResponse {

    private CertificateLimit certificate;

    private EnrollmentLimit enrollment;

    public GetSubjectLimitResponse() {
    }

    public CertificateLimit getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateLimit certificate) {
        this.certificate = certificate;
    }

    public EnrollmentLimit getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(EnrollmentLimit enrollment) {
        this.enrollment = enrollment;
    }
}

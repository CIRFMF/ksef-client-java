package pl.akmf.ksef.sdk.client.model.limit;

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

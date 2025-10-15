package pl.akmf.ksef.sdk.client.model.limit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ChangeSubjectCertificateLimitRequest {
    private EnrollmentLimit enrollment;
    private CertificateLimit certificate;
    private SubjectType subjectIdentifierType;

    public ChangeSubjectCertificateLimitRequest() {

    }

    public ChangeSubjectCertificateLimitRequest(EnrollmentLimit enrollment, CertificateLimit certificate, SubjectType subjectIdentifierType) {
        this.enrollment = enrollment;
        this.certificate = certificate;
        this.subjectIdentifierType = subjectIdentifierType;
    }

    public EnrollmentLimit getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(EnrollmentLimit enrollment) {
        this.enrollment = enrollment;
    }

    public CertificateLimit getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateLimit certificate) {
        this.certificate = certificate;
    }

    public SubjectType getSubjectIdentifierType() {
        return subjectIdentifierType;
    }

    public void setSubjectIdentifierType(SubjectType subjectIdentifierType) {
        this.subjectIdentifierType = subjectIdentifierType;
    }

    public enum SubjectType {
        NIP("Nip"),
        PESEL("Pesel"),
        FINGERPRING("Fingerprint"),
        TOKEN("Token");

        private String value;

        SubjectType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static SubjectType fromValue(String value) {
            for (SubjectType b : SubjectType.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

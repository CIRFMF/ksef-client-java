package pl.akmf.ksef.sdk.client.model.certificate;

import java.time.OffsetDateTime;

/**
 * CertificateEnrollmentResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EnrollCertificateResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateEnrollmentResponse {

    /**
     * Numer referencyjny wniosku certyfikacyjnego.
     */
    private String referenceNumber;

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    private OffsetDateTime timestamp;

    public CertificateEnrollmentResponse() {
    }

    public CertificateEnrollmentResponse(String referenceNumber, OffsetDateTime timestamp) {
        this.referenceNumber = referenceNumber;
        this.timestamp = timestamp;
    }

    /**
     * Numer referencyjny wniosku certyfikacyjnego.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny wniosku certyfikacyjnego.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

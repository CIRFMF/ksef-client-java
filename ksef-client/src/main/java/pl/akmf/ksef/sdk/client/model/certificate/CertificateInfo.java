package pl.akmf.ksef.sdk.client.model.certificate;

import java.time.OffsetDateTime;

/**
 * CertificateInfo.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code CertificateListItem}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CertificateInfo {

    /**
     * Numer seryjny certyfikatu (w formacie szesnastkowym).
     */
    private String certificateSerialNumber;

    /**
     * Nazwa własna certyfikatu.
     */
    private String name;

    /**
     * Typ certyfikatu.
     */
    private CertificateType type;

    /**
     * Nazwa powszechna (CN) podmiotu, dla którego wystawiono certyfikat.
     */
    private String commonName;

    /**
     * Status certyfikatu.
     */
    private CertificateListItemStatus status;

    /**
     * Identyfikator podmiotu, dla którego wystawiono certyfikat.
     */
    private SubjectCertificateIdentifier subjectIdentifier;

    /**
     * Data rozpoczęcia ważności certyfikatu.
     */
    private OffsetDateTime validFrom;

    /**
     * Data wygaśnięcia certyfikatu.
     */
    private OffsetDateTime validTo;

    /**
     * Data ostatniego użycia certyfikatu.
     */
    private OffsetDateTime lastUseDate;

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    private OffsetDateTime requestDate;

    public CertificateInfo() {
    }

    /**
     * Numer seryjny certyfikatu (w formacie szesnastkowym).
     */
    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    /**
     * Numer seryjny certyfikatu (w formacie szesnastkowym).
     */
    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    /**
     * Nazwa własna certyfikatu.
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa własna certyfikatu.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Nazwa powszechna (CN) podmiotu, dla którego wystawiono certyfikat.
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Nazwa powszechna (CN) podmiotu, dla którego wystawiono certyfikat.
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Status certyfikatu.
     */
    public CertificateListItemStatus getStatus() {
        return status;
    }

    /**
     * Status certyfikatu.
     */
    public void setStatus(CertificateListItemStatus status) {
        this.status = status;
    }

    /**
     * Identyfikator podmiotu, dla którego wystawiono certyfikat.
     */
    public SubjectCertificateIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Identyfikator podmiotu, dla którego wystawiono certyfikat.
     */
    public void setSubjectIdentifier(SubjectCertificateIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    /**
     * Data rozpoczęcia ważności certyfikatu.
     */
    public OffsetDateTime getValidFrom() {
        return validFrom;
    }

    /**
     * Data rozpoczęcia ważności certyfikatu.
     */
    public void setValidFrom(OffsetDateTime validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Data wygaśnięcia certyfikatu.
     */
    public OffsetDateTime getValidTo() {
        return validTo;
    }

    /**
     * Data wygaśnięcia certyfikatu.
     */
    public void setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
    }

    /**
     * Data ostatniego użycia certyfikatu.
     */
    public OffsetDateTime getLastUseDate() {
        return lastUseDate;
    }

    /**
     * Data ostatniego użycia certyfikatu.
     */
    public void setLastUseDate(OffsetDateTime lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    /**
     * Typ certyfikatu.
     */
    public CertificateType getType() {
        return type;
    }

    /**
     * Typ certyfikatu.
     */
    public void setType(CertificateType type) {
        this.type = type;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public OffsetDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Data złożenia wniosku certyfikacyjnego.
     */
    public void setRequestDate(OffsetDateTime requestDate) {
        this.requestDate = requestDate;
    }
}

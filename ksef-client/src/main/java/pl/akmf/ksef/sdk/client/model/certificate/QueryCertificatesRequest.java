package pl.akmf.ksef.sdk.client.model.certificate;

import java.time.OffsetDateTime;

/**
 * QueryCertificatesRequest.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryCertificatesRequest {

    /**
     * Numer seryjny certyfikatu. Wyszukiwanie odbywa się na zasadzie dokładnego dopasowania (exact match).
     */
    private String certificateSerialNumber;

    /**
     * Nazwa własna certyfikatu. Wyszukiwanie jest częściowe, czyli zwracane są certyfikaty, których nazwa zawiera podany ciąg znaków (contains).
     */
    private String name;

    /**
     * Typ certyfikatu KSeF.
     */
    private CertificateType type;

    /**
     * Status certyfikatu.
     */
    private CertificateListItemStatus status;

    /**
     * Filtruje certyfikaty, które wygasają po podanej dacie.
     */
    private OffsetDateTime expiresAfter;

    public QueryCertificatesRequest() {
    }

    public QueryCertificatesRequest(String certificateSerialNumber, String name, CertificateType type, CertificateListItemStatus status, OffsetDateTime expiresAfter) {
        this.certificateSerialNumber = certificateSerialNumber;
        this.name = name;
        this.type = type;
        this.status = status;
        this.expiresAfter = expiresAfter;
    }

    /**
     * Numer seryjny certyfikatu. Wyszukiwanie odbywa się na zasadzie dokładnego dopasowania (exact match).
     */
    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    /**
     * Numer seryjny certyfikatu. Wyszukiwanie odbywa się na zasadzie dokładnego dopasowania (exact match).
     */
    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    /**
     * Nazwa własna certyfikatu. Wyszukiwanie jest częściowe, czyli zwracane są certyfikaty, których nazwa zawiera podany ciąg znaków (contains).
     */
    public String getName() {
        return name;
    }

    /**
     * Nazwa własna certyfikatu. Wyszukiwanie jest częściowe, czyli zwracane są certyfikaty, których nazwa zawiera podany ciąg znaków (contains).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Typ certyfikatu KSeF.
     */
    public CertificateType getType() {
        return type;
    }

    /**
     * Typ certyfikatu KSeF.
     */
    public void setType(CertificateType type) {
        this.type = type;
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
     * Filtruje certyfikaty, które wygasają po podanej dacie.
     */
    public OffsetDateTime getExpiresAfter() {
        return expiresAfter;
    }

    /**
     * Filtruje certyfikaty, które wygasają po podanej dacie.
     */
    public void setExpiresAfter(OffsetDateTime expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}

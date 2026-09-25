package pl.akmf.ksef.sdk.client.model.certificate.publickey;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PublicKeyCertificate.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class PublicKeyCertificate {

    /**
     * Certyfikat klucza publicznego w formacie DER, zakodowany w formacie Base64.
     */
    private String certificate;

    /**
     * Identyfikator certyfikatu.
     */
    private String certificateId;

    /**
     * Identyfikator klucza, używany jako selektor w wywołaniach, w których klient wskazuje, jakiego klucza publicznego użył do szyfrowania.
     */
    private String publicKeyId;

    /**
     * Data początku obowiązywania certyfikatu.
     */
    private OffsetDateTime validFrom;

    /**
     * Data końca obowiązywania certyfikatu.
     */
    private OffsetDateTime validTo;

    /**
     * Operacje do których może być używany certyfikat.
     */
    private List<PublicKeyCertificateUsage> usage = new ArrayList<>();

    public PublicKeyCertificate() {
    }

    /**
     * Certyfikat klucza publicznego w formacie DER, zakodowany w formacie Base64.
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * Certyfikat klucza publicznego w formacie DER, zakodowany w formacie Base64.
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    /**
     * Data początku obowiązywania certyfikatu.
     */
    public OffsetDateTime getValidFrom() {
        return validFrom;
    }

    /**
     * Data początku obowiązywania certyfikatu.
     */
    public void setValidFrom(OffsetDateTime validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Data końca obowiązywania certyfikatu.
     */
    public OffsetDateTime getValidTo() {
        return validTo;
    }

    /**
     * Data końca obowiązywania certyfikatu.
     */
    public void setValidTo(OffsetDateTime validTo) {
        this.validTo = validTo;
    }

    /**
     * Operacje do których może być używany certyfikat.
     */
    public List<PublicKeyCertificateUsage> getUsage() {
        return usage;
    }

    /**
     * Operacje do których może być używany certyfikat.
     */
    public void setUsage(List<PublicKeyCertificateUsage> usage) {
        this.usage = usage;
    }

    /**
     * Identyfikator certyfikatu.
     */
    public String getCertificateId() {
        return certificateId;
    }

    /**
     * Identyfikator certyfikatu.
     */
    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    /**
     * Identyfikator klucza, używany jako selektor w wywołaniach, w których klient wskazuje, jakiego klucza publicznego użył do szyfrowania.
     */
    public String getPublicKeyId() {
        return publicKeyId;
    }

    /**
     * Identyfikator klucza, używany jako selektor w wywołaniach, w których klient wskazuje, jakiego klucza publicznego użył do szyfrowania.
     */
    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }
}

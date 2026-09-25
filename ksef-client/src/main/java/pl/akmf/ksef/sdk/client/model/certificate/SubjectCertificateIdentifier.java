package pl.akmf.ksef.sdk.client.model.certificate;

/**
 * Identyfikator podmiotu, dla którego wystawiono certyfikat.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code CertificateSubjectIdentifier}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SubjectCertificateIdentifier {

    /**
     * Typ identyfikatora.
     */
    private SubjectCertificateIdentifierType type;

    /**
     * Wartość identyfikatora.
     */
    private String value;

    public SubjectCertificateIdentifier() {
    }

    /**
     * Typ identyfikatora.
     */
    public SubjectCertificateIdentifierType getType() {
        return type;
    }

    /**
     * Typ identyfikatora.
     */
    public void setType(SubjectCertificateIdentifierType type) {
        this.type = type;
    }

    /**
     * Wartość identyfikatora.
     */
    public String getValue() {
        return value;
    }

    /**
     * Wartość identyfikatora.
     */
    public void setValue(String value) {
        this.value = value;
    }
}

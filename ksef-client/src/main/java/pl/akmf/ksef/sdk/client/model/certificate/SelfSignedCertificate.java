package pl.akmf.ksef.sdk.client.model.certificate;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * Wynik lokalnego (klienckiego) wygenerowania samopodpisanego certyfikatu wraz
 * z parą kluczy. Operacja czysto kryptograficzna wykonywana po stronie SDK.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SelfSignedCertificate {

    private final X509Certificate certificate;

    private final KeyPair keyPair;

    public SelfSignedCertificate(X509Certificate certificate, KeyPair keyPair) {
        this.certificate = certificate;
        this.keyPair = keyPair;
    }

    public X509Certificate certificate() {
        return certificate;
    }

    public KeyPair keyPair() {
        return keyPair;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (SelfSignedCertificate) obj;
        return Objects.equals(this.certificate, that.certificate) && Objects.equals(this.keyPair, that.keyPair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificate, keyPair);
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }
}

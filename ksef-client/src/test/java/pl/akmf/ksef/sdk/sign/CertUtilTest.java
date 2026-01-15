package pl.akmf.ksef.sdk.sign;

import org.junit.Test;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.api.services.DefaultCertificateService;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingEcdsaPair;
import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingRsaPair;

public class CertUtilTest {

    private static final String SHA_256_WITH_RSA = "SHA256withRSA";
    private static final String SHA_256_WITH_ECDSA = "SHA256withECDSA";

    private static final CertificateBuilders.X500NameHolder x500Name = new CertificateBuilders()
            .buildForOrganization("Kowalski sp. z o.o", "1111111111", "commonName", "PL");

    @Test
    // RSA: should detect matching certificate and private key
    public void testMatchingRsaPair() {
        SelfSignedCertificate rsaDto = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500Name);
        X509Certificate cert = rsaDto.certificate();
        PrivateKey key = rsaDto.keyPair().getPrivate();

        assertTrue(isMatchingRsaPair(cert, key));
        assertTrue(checkSignature(cert.getPublicKey(), key, SHA_256_WITH_RSA));
        assertFalse(isMatchingEcdsaPair(cert, key));
    }

    @Test
    // RSA: should detect non-matching private key
    public void testNonMatchingRsaPair() {
        SelfSignedCertificate rsa1 = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500Name);
        SelfSignedCertificate rsa2 = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500Name);

        assertTrue(isMatchingRsaPair(rsa1.certificate(), rsa2.keyPair().getPrivate()));
        assertFalse(checkSignature(rsa1.keyPair().getPublic(), rsa2.keyPair().getPrivate(), SHA_256_WITH_RSA));
    }

    @Test
    // ECDSA: should detect matching EC key pair
    public void testMatchingEcdsaPair_EC() {
        SelfSignedCertificate ecdsaDto = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500Name);
        X509Certificate cert = ecdsaDto.certificate();
        PrivateKey key = ecdsaDto.keyPair().getPrivate();

        assertTrue(isMatchingEcdsaPair(cert, key));
        assertTrue(checkSignature(cert.getPublicKey(), key, SHA_256_WITH_ECDSA));
        assertFalse(isMatchingRsaPair(cert, key));
    }

    @Test
    // Mismatched keys: RSA cert + ECDSA key
    public void testMismatchedKeys() {
        // RSA cert
        SelfSignedCertificate rsaDto = new DefaultCertificateService().generateSelfSignedCertificateRsa(x500Name);
        X509Certificate rsaCert = rsaDto.certificate();

        // ECDSA key
        SelfSignedCertificate ecdsaDto = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500Name);
        KeyPair ecKeyPair = ecdsaDto.keyPair();

        assertFalse(isMatchingRsaPair(rsaCert, ecKeyPair.getPrivate()));
        assertFalse(isMatchingEcdsaPair(rsaCert, ecKeyPair.getPrivate()));
    }

    @Test
    // ECDSA: should detect non-matching private key
    public void testNonMatchingEcdsaPairs() {
        SelfSignedCertificate ecdsa1 = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500Name);
        SelfSignedCertificate ecdsa2 = new DefaultCertificateService().generateSelfSignedCertificateEcdsa(x500Name);

        assertTrue(isMatchingEcdsaPair(ecdsa1.certificate(), ecdsa2.keyPair().getPrivate()));
        assertFalse(checkSignature(ecdsa1.keyPair().getPublic(), ecdsa2.keyPair().getPrivate(), SHA_256_WITH_ECDSA));
    }

    @Test
    public void testNullParameters() {
        assertFalse(isMatchingEcdsaPair(null, null));
        assertFalse(isMatchingRsaPair(null, null));
    }

    private static boolean checkSignature(PublicKey pubKey, PrivateKey privateKey, String algorithm) {
        try {
            byte[] testData = "KeyPairTestData".getBytes();

            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(testData);
            byte[] sigBytes = signature.sign();

            signature.initVerify(pubKey);
            signature.update(testData);
            return signature.verify(sigBytes);
        } catch (Exception e) {
            return false;
        }
    }
}
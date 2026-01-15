package pl.akmf.ksef.sdk.api.services;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.interfaces.CertificateService;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DefaultCertificateService implements CertificateService {
    private static final String RSA = "RSA";
    private static final String EC = "EC";
    private static final String SHA_256_WITH_RSA = "SHA256WithRSA";
    private static final String SHA_256_WITH_ECDSA = "SHA256withECDSA";
    private static final String BC = "BC";
    private static final String SHA_256 = "SHA-256";

    @Override
    public String getSha256Fingerprint(X509Certificate certificate) {
        try {
            byte[] raw = certificate.getEncoded();
            MessageDigest sha256 = MessageDigest.getInstance(SHA_256);
            byte[] sha256Bytes = sha256.digest(raw);

            StringBuilder hexString = new StringBuilder();
            for (byte b : sha256Bytes) {
                String hex = String.format("%02X", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (CertificateEncodingException | NoSuchAlgorithmException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName) {
        return getPersonalCertificate(givenName, surname, serialNumberPrefix, serialNumber, commonName, EncryptionMethod.Rsa);
    }

    @Override
    public SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName, EncryptionMethod encryptionMethod) {
        CertificateBuilders.X500NameHolder x500Name = new CertificateBuilders()
                .buildForPerson(givenName, surname, serialNumberPrefix + "-" + serialNumber, commonName, "PL");

        switch (encryptionMethod) {
            case Rsa:
                return generateSelfSignedCertificateRsa(x500Name);
            case ECDsa:
                return generateSelfSignedCertificateEcdsa(x500Name);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName) {
        return getCompanySeal(organizationName, organizationIdentifier, commonName, EncryptionMethod.Rsa);
    }

    @Override
    public SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName, EncryptionMethod encryptionMethod) {
        CertificateBuilders.X500NameHolder x500Name = new CertificateBuilders()
                .buildForOrganization(organizationName, organizationIdentifier, commonName, "PL");

        switch (encryptionMethod) {
            case Rsa:
                return generateSelfSignedCertificateRsa(x500Name);
            case ECDsa:
                return generateSelfSignedCertificateEcdsa(x500Name);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public SelfSignedCertificate generateSelfSignedCertificateRsa(CertificateBuilders.X500NameHolder x500Name) {

        return generateSelfSignedCertificate(RSA, 2048, SHA_256_WITH_RSA, x500Name.getX500Name());
    }

    @Override
    public SelfSignedCertificate generateSelfSignedCertificateEcdsa(CertificateBuilders.X500NameHolder x500Name) {

        return generateSelfSignedCertificate(EC, 256, SHA_256_WITH_ECDSA, x500Name.getX500Name());
    }

    private SelfSignedCertificate generateSelfSignedCertificate(String generatorAlgorithm,
                                                                int generatorKeySize,
                                                                String signatureAlgorithm,
                                                                X500Name x500Name) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(generatorAlgorithm);
            keyPairGenerator.initialize(generatorKeySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            Instant now = Instant.now();
            Date notBefore = Date.from(now);
            Date notAfter = Date.from(now.plus(365, ChronoUnit.DAYS));

            BigInteger certSerialNumber = new BigInteger(Long.toString(System.currentTimeMillis()));

            JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                    x500Name,
                    certSerialNumber,
                    notBefore,
                    notAfter,
                    x500Name,
                    keyPair.getPublic()
            );

            ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm)
                    .setProvider(BC)
                    .build(keyPair.getPrivate());

            X509CertificateHolder certHolder = certBuilder.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter()
                    .setProvider(BC);
            X509Certificate certificate = certConverter.getCertificate(certHolder);

            return new SelfSignedCertificate(certificate, keyPair);
        } catch (Exception e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }
}

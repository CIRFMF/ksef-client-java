package pl.akmf.ksef.sdk.api.services;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.interfaces.CryptographyService;
import pl.akmf.ksef.sdk.client.interfaces.KSeFClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.publickey.PublicKeyCertificate;
import pl.akmf.ksef.sdk.client.model.certificate.publickey.PublicKeyCertificateUsage;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.system.CryptographyException;
import pl.akmf.ksef.sdk.system.KsefIntegrationMode;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

public class DefaultCryptographyService implements CryptographyService {
    private static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final String RSA_ECB_OAEPPADDING = "RSA/ECB/OAEPPadding";
    private static final String AES = "AES";
    private static final String RSA = "RSA";
    private static final String SHA_256_WITH_RSA = "SHA256withRSA";
    private static final String SHA_256_WITH_ECDSA = "SHA256withECDSA";
    private static final String SHA_256 = "SHA-256";
    private static final String ECDH = "ECDH";
    private static final String MGF_1 = "MGF1";
    private static final String EC = "EC";
    private static final String BC = "BC";
    private static final String SECP_256_R_1 = "secp256r1";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n";
    private static final String END_CERTIFICATE = "\n-----END CERTIFICATE-----";
    private static final String X_509 = "X.509";
    private static final Logger log = LoggerFactory.getLogger(DefaultCryptographyService.class);
    private String symmetricKeyEncryptionPem;
    private String ksefTokenPem;
    private final KSeFClient ksefClient;
    private String secureRandomAlgorithm = null;
    private KsefIntegrationMode ksefIntegrationMode = KsefIntegrationMode.OFFLINE;

    public DefaultCryptographyService(KSeFClient ksefClient) throws SystemKSeFSDKException {
        this.ksefClient = ksefClient;
        initCryptographyService();
    }

    public DefaultCryptographyService(KSeFClient ksefClient, String secureRandomAlgorithm) throws SystemKSeFSDKException {
        this.ksefClient = ksefClient;
        this.secureRandomAlgorithm = secureRandomAlgorithm;
        initCryptographyService();
    }

    @Override
    public EncryptionData getEncryptionData() throws SystemKSeFSDKException {
        validateServiceConfiguration();
        try {
            PublicKey publicKey = parsePublicKeyFromCertificatePem(this.symmetricKeyEncryptionPem);

            byte[] key = generateRandom256BitsKey();

            byte[] iv = generateRandom16BytesIv();

            byte[] encryptedKey = encryptWithRSAUsingPublicKey(key, publicKey);
            String encodedEncryptedKey = Base64.getEncoder().encodeToString(encryptedKey);
            String initializationVector = Base64.getEncoder().encodeToString(iv);

            EncryptionInfo encryptionInfo = new EncryptionInfo();
            encryptionInfo.setEncryptedSymmetricKey(encodedEncryptedKey);
            encryptionInfo.setInitializationVector(initializationVector);

            return new EncryptionData(key, iv, encodedEncryptedKey, encryptionInfo);
        } catch (NoSuchAlgorithmException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] encryptKsefTokenWithRSAUsingPublicKey(String ksefToken, Instant challengeTimestamp) throws SystemKSeFSDKException {
        validateServiceConfiguration();
        byte[] tokenWithTimestamp = (ksefToken + "|" + challengeTimestamp.toEpochMilli())
                .getBytes(StandardCharsets.UTF_8);

        return encryptWithRSAUsingPublicKey(tokenWithTimestamp);
    }

    @Override
    public byte[] encryptKsefTokenWithECDsaUsingPublicKey(String ksefToken, Instant challengeTimestamp) {
        validateServiceConfiguration();
        byte[] tokenWithTimestamp = (ksefToken + "|" + challengeTimestamp.toEpochMilli())
                .getBytes(StandardCharsets.UTF_8);

        return encryptWithECDsaUsingPublicKey(tokenWithTimestamp);
    }

    @Override
    public byte[] encryptWithRSAUsingPublicKey(byte[] content) throws SystemKSeFSDKException {
        validateServiceConfiguration();
        PublicKey publicKey = parsePublicKeyFromCertificatePem(this.ksefTokenPem);

        return encryptWithRSAUsingPublicKey(content, publicKey);
    }

    @Override
    public byte[] encryptWithECDsaUsingPublicKey(byte[] content) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            ECPublicKey publicKey = (ECPublicKey) parsePublicKeyFromCertificatePem(this.ksefTokenPem);

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(EC);
            kpg.initialize(new ECGenParameterSpec(SECP_256_R_1));
            KeyPair ephemeralKeyPair = kpg.generateKeyPair();

            KeyAgreement keyAgreement = KeyAgreement.getInstance(ECDH);
            keyAgreement.init(ephemeralKeyPair.getPrivate());
            keyAgreement.doPhase(publicKey, true);
            byte[] sharedSecret = keyAgreement.generateSecret();

            SecretKey aesKey = new SecretKeySpec(sharedSecret, 0, 32, AES);

            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
            byte[] ciphertextWithTag = cipher.doFinal(content);

            byte[] ephemeralPubEncoded = ephemeralKeyPair.getPublic().getEncoded();

            ByteBuffer buffer = ByteBuffer.allocate(ephemeralPubEncoded.length + nonce.length + ciphertextWithTag.length);
            buffer.put(ephemeralPubEncoded);
            buffer.put(nonce);
            buffer.put(ciphertextWithTag);

            return buffer.array();
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] decryptBytesWithAes256(byte[] encryptedPackagePart, byte[] cipherKey, byte[] cipherIv) {
        validateServiceConfiguration();

        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
            SecretKeySpec secretKeySpec = new SecretKeySpec(cipherKey, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(cipherIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

            try (InputStream inputStream = new ByteArrayInputStream(encryptedPackagePart);
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byte[] decrypted = cipher.update(buffer, 0, bytesRead);
                    if (decrypted != null) {
                        outputStream.write(decrypted);
                    }
                }

                byte[] finalBlock = cipher.doFinal();
                if (finalBlock != null) {
                    outputStream.write(finalBlock);
                }

                return outputStream.toByteArray();
            } catch (IOException | BadPaddingException | IllegalBlockSizeException e) {
                throw new SystemKSeFSDKException(e.getMessage(), e);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public void decryptStreamBytesWithAes256(InputStream encryptedPackagePart, OutputStream output, byte[] cipherKey, byte[] cipherIv) {
        validateServiceConfiguration();

        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(cipherKey, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(cipherIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            try (CipherInputStream cipherInputStream = new CipherInputStream(encryptedPackagePart, cipher)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush();
            } catch (IOException e) {
                throw new SystemKSeFSDKException(e.getMessage(), e);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] encryptBytesWithAES256(byte[] content, byte[] key, byte[] iv) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);

            SecretKeySpec secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return cipher.doFinal(content);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public void encryptStreamWithAES256(InputStream input, OutputStream output, byte[] key, byte[] iv) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            try (CipherOutputStream cipherOut = new CipherOutputStream(output, cipher)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    cipherOut.write(buffer, 0, bytesRead);
                }
                cipherOut.flush();
            } catch (IOException e) {
                throw new SystemKSeFSDKException(e.getMessage(), e);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public CsrResult generateCsrWithRsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);

            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            X500Name subject = getX500Name(certificateInfo).getX500Name();

            PKCS10CertificationRequestBuilder requestBuilder =
                    new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());

            ContentSigner signer = new JcaContentSignerBuilder(SHA_256_WITH_RSA)
                    .build(keyPair.getPrivate());

            PKCS10CertificationRequest csr = requestBuilder.build(signer);

            byte[] csrDer = csr.toASN1Structure().getEncoded(ASN1Encoding.DER);

            return new CsrResult(csrDer, keyPair.getPrivate().getEncoded());
        } catch (IOException | OperatorCreationException | NoSuchAlgorithmException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public CsrResult generateCsrWithEcdsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(EC);
            kpg.initialize(new ECGenParameterSpec(SECP_256_R_1));
            KeyPair keyPair = kpg.generateKeyPair();

            X500Name subject = getX500Name(certificateInfo).getX500Name();

            JcaPKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());

            ContentSigner signer = new JcaContentSignerBuilder(SHA_256_WITH_ECDSA)
                    .build(keyPair.getPrivate());

            PKCS10CertificationRequest csr = csrBuilder.build(signer);

            byte[] csrDer = csr.toASN1Structure().getEncoded(ASN1Encoding.DER);

            return new CsrResult(csrDer, keyPair.getPrivate().getEncoded());
        } catch (IOException | OperatorCreationException | InvalidAlgorithmParameterException |
                 NoSuchAlgorithmException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public FileMetadata getMetaData(byte[] file) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            MessageDigest sha256 = MessageDigest.getInstance(SHA_256);
            byte[] hash = sha256.digest(file);
            String base64Hash = Base64.getEncoder().encodeToString(hash);

            int fileSize = file.length;

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setFileSize((long) fileSize);
            fileMetadata.setHashSHA(base64Hash);

            return fileMetadata;
        } catch (NoSuchAlgorithmException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public FileMetadata getMetaData(InputStream inputStream) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream cannot be null");
        }

        try {
            MessageDigest sha256 = MessageDigest.getInstance(SHA_256);
            byte[] buffer = new byte[8192];
            int bytesRead;
            int fileSize = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                sha256.update(buffer, 0, bytesRead);
                fileSize += bytesRead;
            }

            byte[] digest = sha256.digest();
            String base64Hash = Base64.getEncoder().encodeToString(digest);

            return new FileMetadata(fileSize, base64Hash);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public PublicKey parsePublicKeyFromCertificatePem(String certificatePem) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try (ByteArrayInputStream input = new ByteArrayInputStream(certificatePem.getBytes(StandardCharsets.UTF_8))) {
            CertificateFactory factory = CertificateFactory.getInstance(X_509);
            X509Certificate certificate = (X509Certificate) factory.generateCertificate(input);
            return certificate.getPublicKey();
        } catch (CertificateException | IOException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public PrivateKey parseRsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException {
        validateServiceConfiguration();

        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);

            KeyFactory keyFactory = KeyFactory.getInstance(RSA);

            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public PrivateKey parseEcdsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException {
        validateServiceConfiguration();
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);

            KeyFactory keyFactory = KeyFactory.getInstance(EC);

            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public PrivateKey parseEncryptedEcdsaPrivateKeyFromPem(byte[] pemBytes, char[] password) {
        try (PEMParser parser = new PEMParser(
                new InputStreamReader(new ByteArrayInputStream(pemBytes)))
        ) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            Object object = parser.readObject();

            if (!(object instanceof PKCS8EncryptedPrivateKeyInfo)) {
                throw new IllegalArgumentException("Provided PEM is not an encrypted private key (BEGIN ENCRYPTED PRIVATE KEY)");
            }
            PKCS8EncryptedPrivateKeyInfo encryptedKeyPair = (PKCS8EncryptedPrivateKeyInfo) object;

            var decryptorProvider = new JcePKCSPBEInputDecryptorProviderBuilder()
                    .setProvider(BC)
                    .build(password);

            PrivateKeyInfo privateKeyInfo = encryptedKeyPair.decryptPrivateKeyInfo(decryptorProvider);

            return new JcaPEMKeyConverter()
                    .setProvider(BC)
                    .getPrivateKey(privateKeyInfo);
        } catch (IOException | PKCSException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    @Override
    public X509Certificate parseCertificateFromBytes(byte[] certBytes) throws CertificateException {
        validateServiceConfiguration();

        CertificateFactory certFactory = CertificateFactory.getInstance(X_509);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(certBytes);
        return (X509Certificate) certFactory.generateCertificate(inputStream);
    }

    @Override
    public void initCryptographyService() {
        try {
            List<PublicKeyCertificate> publicKeyCertificates = ksefClient.retrievePublicKeyCertificate();

            this.symmetricKeyEncryptionPem = publicKeyCertificates.stream()
                    .filter(c -> c.getUsage().contains(PublicKeyCertificateUsage.SYMMETRICKEYENCRYPTION))
                    .findFirst()
                    .map(PublicKeyCertificate::getCertificate)
                    .map(c -> BEGIN_CERTIFICATE + c + END_CERTIFICATE)
                    .orElse(null);

            this.ksefTokenPem = publicKeyCertificates.stream()
                    .filter(c -> c.getUsage().contains(PublicKeyCertificateUsage.KSEFTOKENENCRYPTION))
                    .min(Comparator.comparing(PublicKeyCertificate::getValidFrom))
                    .map(PublicKeyCertificate::getCertificate)
                    .map(c -> BEGIN_CERTIFICATE + c + END_CERTIFICATE)
                    .orElse(null);
            ksefIntegrationMode = KsefIntegrationMode.ONLINE;
        } catch (ApiException | SystemKSeFSDKException e) {
            ksefIntegrationMode = KsefIntegrationMode.OFFLINE;
            log.error("Error with connection to KseF Api: {}", e.getMessage() + ". Library works in offline mode");
        }
    }

    @Override
    public KsefIntegrationMode getKsefIntegrationMode() {
        return ksefIntegrationMode;
    }

    private byte[] encryptWithRSAUsingPublicKey(byte[] content, PublicKey publicKey) throws SystemKSeFSDKException {
        try {
            OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                    SHA_256,
                    MGF_1,
                    MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT
            );

            Cipher cipher = Cipher.getInstance(RSA_ECB_OAEPPADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);

            return cipher.doFinal(content);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new SystemKSeFSDKException(e.getMessage(), e);
        }
    }

    private static CertificateBuilders.X500NameHolder getX500Name(CertificateEnrollmentsInfoResponse certificateInfo) {
        return new CertificateBuilders()
                .withCommonName(certificateInfo.getCommonName())
                .withSurname(certificateInfo.getSurname())
                .withOrganizationName(certificateInfo.getOrganizationName())
                .withOrganizationIdentifier(certificateInfo.getOrganizationIdentifier())
                .withCountryCode(certificateInfo.getCountryName())
                .withSerialNumber(certificateInfo.getSerialNumber())
                .withUniqueIdentifier(certificateInfo.getUniqueIdentifier())
                .withGivenName(certificateInfo.getGivenName())
                .build();
    }

    private byte[] generateRandom256BitsKey() throws NoSuchAlgorithmException {
        byte[] key = new byte[256 / 8];
        if (secureRandomAlgorithm == null) {
            new SecureRandom().nextBytes(key);
        } else {
            SecureRandom.getInstance(secureRandomAlgorithm).nextBytes(key);
        }
        return key;
    }

    private byte[] generateRandom16BytesIv() throws NoSuchAlgorithmException {
        byte[] iv = new byte[16];
        if (secureRandomAlgorithm == null) {
            new SecureRandom().nextBytes(iv);
        } else {
            SecureRandom.getInstance(secureRandomAlgorithm).nextBytes(iv);
        }
        return iv;
    }

    private void validateServiceConfiguration() {
        if (ksefIntegrationMode.equals(KsefIntegrationMode.OFFLINE)) {
            throw new CryptographyException("Service has not been initialized correctly");
        }
    }
}
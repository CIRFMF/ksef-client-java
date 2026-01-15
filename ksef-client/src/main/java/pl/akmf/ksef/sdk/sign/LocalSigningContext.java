package pl.akmf.ksef.sdk.sign;

import eu.europa.esig.dss.enumerations.SignatureAlgorithm;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.KSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;

import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingEcdsaPair;
import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingRsaPair;

public class LocalSigningContext implements SignContextProvider {
    public static final String PKCS_12 = "PKCS12";
    public static final String CUSTOM_KEYSTORE_ALIAS = "alias";

    @Override
    public SignatureValue createSignatureValue(ToBeSigned toBeSigned, X509Certificate signatureCertificate, PrivateKey privateKey) {
        try {
            KeyStore keystore = KeyStore.getInstance(PKCS_12);
            keystore.load(null, null); // Initialize empty keystore
            Certificate[] chain = new Certificate[]{signatureCertificate};

            keystore.setKeyEntry(
                    CUSTOM_KEYSTORE_ALIAS,
                    privateKey,
                    null,
                    chain
            );

            // Convert keystore to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            keystore.store(baos, null);
            byte[] keystoreBytes = baos.toByteArray();

            DSSPrivateKeyEntry dssPrivateKeyEntry = getPrivateKeyEntry(keystore);

            SignatureAlgorithm signatureAlgorithm;
            if (isMatchingRsaPair(signatureCertificate, privateKey)) {
                signatureAlgorithm = SignatureAlgorithm.RSA_SHA256;
            } else if (isMatchingEcdsaPair(signatureCertificate, privateKey)) {
                ECPublicKey ecPublicKey = (ECPublicKey) signatureCertificate.getPublicKey();
                int keySize = ecPublicKey.getParams().getCurve().getField().getFieldSize();

                if (keySize <= 256) {
                    signatureAlgorithm = SignatureAlgorithm.ECDSA_SHA256;
                } else if (keySize <= 384) {
                    signatureAlgorithm = SignatureAlgorithm.ECDSA_SHA384;
                } else {
                    signatureAlgorithm = SignatureAlgorithm.ECDSA_SHA512;
                }
            } else {
                throw new IllegalArgumentException("Encoding data are incorrect: \nCertificate signatureAlg: " + signatureCertificate.getSigAlgName()
                + "\nPublicKey signatureAlg: " + signatureCertificate.getPublicKey().getAlgorithm()
                + "\nPrivateKEy signatureAlg: " + privateKey.getAlgorithm());
            }

            try (Pkcs12SignatureToken token = createPkcs12Token(keystoreBytes)) {
                return token.sign(toBeSigned, signatureAlgorithm, dssPrivateKeyEntry);
            }
        } catch (UnrecoverableEntryException | CertificateException | IOException | NoSuchAlgorithmException |
                 KeyStoreException e) {
            throw new SignSignatureException("Failed to create signature value", e);
        }
    }

    private DSSPrivateKeyEntry getPrivateKeyEntry(KeyStore keyStore) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.PasswordProtection keyPasswordProtection = new KeyStore.PasswordProtection(null);
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(CUSTOM_KEYSTORE_ALIAS, keyPasswordProtection);
        return new KSPrivateKeyEntry(CUSTOM_KEYSTORE_ALIAS, privateKeyEntry);
    }

    private Pkcs12SignatureToken createPkcs12Token(byte[] keystoreBytes) {
        return new Pkcs12SignatureToken(keystoreBytes, new KeyStore.PasswordProtection(null));
    }
}

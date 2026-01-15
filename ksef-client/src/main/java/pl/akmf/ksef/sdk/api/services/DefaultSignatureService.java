package pl.akmf.ksef.sdk.api.services;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.EncryptionAlgorithm;
import eu.europa.esig.dss.enumerations.MimeTypeEnum;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import pl.akmf.ksef.sdk.client.interfaces.SignatureService;
import pl.akmf.ksef.sdk.sign.LocalSigningContext;
import pl.akmf.ksef.sdk.sign.SignContextProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;

import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingEcdsaPair;
import static pl.akmf.ksef.sdk.sign.CertUtil.isMatchingRsaPair;

public class DefaultSignatureService implements SignatureService {

    public String sign(byte[] xml, X509Certificate signatureCertificate, PrivateKey privateKey) throws IOException {
        SignContextProvider signContextProvider = new LocalSigningContext();

        DSSDocument toSignDocument = new InMemoryDocument(xml, null, MimeTypeEnum.XML);

        CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
        XAdESService service = new XAdESService(commonCertificateVerifier);

        XAdESSignatureParameters parameters = prepareParameters(signatureCertificate, privateKey);
        ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);
        SignatureValue signatureValue = signContextProvider.createSignatureValue(dataToSign, signatureCertificate, privateKey);

        DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
        try (ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) signedDocument.openStream()) {
            return new String(byteArrayInputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private XAdESSignatureParameters prepareParameters(X509Certificate x509Certificate, PrivateKey privateKey) {
        XAdESSignatureParameters parameters = new XAdESSignatureParameters();
        parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);
        parameters.setSignatureLevel(SignatureLevel.XAdES_BASELINE_B);
        parameters.setEn319132(false);

        EncryptionAlgorithm encryptionAlgorithm;
        DigestAlgorithm digestAlgorithm = DigestAlgorithm.SHA256;

        if (isMatchingRsaPair(x509Certificate, privateKey)) {
            encryptionAlgorithm = EncryptionAlgorithm.RSA;
        } else if (isMatchingEcdsaPair(x509Certificate, privateKey)) {
            encryptionAlgorithm = EncryptionAlgorithm.ECDSA;

            ECPublicKey ecPublicKey = (ECPublicKey) x509Certificate.getPublicKey();

            int fieldSize = ecPublicKey.getParams().getCurve().getField().getFieldSize();
            if (fieldSize <= 256) {
                digestAlgorithm = DigestAlgorithm.SHA256;
            } else if (fieldSize <= 384) {
                digestAlgorithm = DigestAlgorithm.SHA384;
            } else {
                digestAlgorithm = DigestAlgorithm.SHA512;
            }
        } else {
            throw new IllegalArgumentException("Encoding data are incorrect: \nCertificate signatureAlg: " + x509Certificate.getSigAlgName()
                    + "\nPublicKey signatureAlg: " + x509Certificate.getPublicKey().getAlgorithm()
                    + "\nPrivateKEy signatureAlg: " + privateKey.getAlgorithm());
        }

        parameters.setEncryptionAlgorithm(encryptionAlgorithm);
        parameters.setDigestAlgorithm(digestAlgorithm);
        parameters.setSigningCertificateDigestMethod(digestAlgorithm);
        parameters.setSigningCertificate(new CertificateToken(x509Certificate));

        return parameters;
    }
}

package pl.akmf.ksef.sdk.api.services;

import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.client.model.qrcode.ContextIdentifierType;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class DefaultVerificationLinkService implements VerificationLinkService {

    private static final String RSASSA_PSS = "RSASSA-PSS";
    private static final String SHA_256 = "SHA-256";
    private static final String MGF_1 = "MGF1";
    private static final String SHA_256_WITH_ECDSA = "SHA256withECDSA";
    private final String qrAppUrl;

    public DefaultVerificationLinkService(String qrAppUrl) {
        this.qrAppUrl = qrAppUrl;
    }

    @Override
    public String buildInvoiceVerificationUrl(String nip, LocalDate issueDate, String invoiceHash) {
        String date = issueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        byte[] invoiceHashBytes = Base64.getDecoder().decode(invoiceHash);
        String invoiceHashUrlEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(invoiceHashBytes);

        return String.format("%s/invoice/%s/%s/%s", qrAppUrl, nip, date, invoiceHashUrlEncoded);
    }

    @Override
    public String buildCertificateVerificationUrl(String sellerNip,
                                                  ContextIdentifierType contextIdentifierType,
                                                  String contextIdentifierValue,
                                                  String certificateSerial,
                                                  String invoiceHash,
                                                  PrivateKey privateKey) {
        byte[] invoiceHashBytes = Base64.getDecoder().decode(invoiceHash);
        String invoiceHashUrlEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(invoiceHashBytes);

        String pathToSign = String.format("%s/certificate/%s/%s/%s/%s/%s", qrAppUrl, contextIdentifierType, contextIdentifierValue, sellerNip, certificateSerial, invoiceHashUrlEncoded)
                .replace("https://", "");
        String signedHash = computeUrlEncodedSignedHash(pathToSign, privateKey);

        return String.format("%s/certificate/%s/%s/%s/%s/%s/%s", qrAppUrl, contextIdentifierType, contextIdentifierValue, sellerNip,
                certificateSerial, invoiceHashUrlEncoded, signedHash);
    }

    private String computeUrlEncodedSignedHash(String pathToSign, PrivateKey privateKey) {
        try {
            Signature signature;
            if (privateKey instanceof RSAPrivateKey) {
                signature = Signature.getInstance(RSASSA_PSS);
                PSSParameterSpec pssSpec = new PSSParameterSpec(SHA_256, MGF_1, new MGF1ParameterSpec(SHA_256), 32, 1);
                signature.setParameter(pssSpec);
            } else if (privateKey instanceof ECPrivateKey) {
                signature = Signature.getInstance(SHA_256_WITH_ECDSA);
            } else {
                throw new SystemKSeFSDKException("Certificate not support RSA or ECDsa.", null);
            }

            signature.initSign(privateKey);
            signature.update(pathToSign.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new SystemKSeFSDKException("Cannot compute signature", e);
        }
    }
}

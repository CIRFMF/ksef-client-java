package pl.akmf.ksef.sdk.sign;

import eu.europa.esig.dss.enumerations.SignatureAlgorithm;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface SignContextProvider {

    @Deprecated
    SignatureValue createSignatureValue(ToBeSigned toBeSigned, X509Certificate signatureCertificate, PrivateKey privateKey);

    SignatureValue createSignatureValue(ToBeSigned toBeSigned, SignatureAlgorithm signatureAlgorithm, X509Certificate signatureCertificate, PrivateKey privateKey);

    SignatureValue createSignatureValue(ToBeSigned toBeSigned, SignatureAlgorithm signatureAlgorithm, PrivateKey privateKey);
}

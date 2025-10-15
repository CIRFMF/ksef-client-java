package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.security.cert.X509Certificate;

public interface CertificateService {

    String getSha256Fingerprint(X509Certificate certificate) throws SystemKSeFSDKException;

    SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName);

    SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName);

    SelfSignedCertificate generateSelfSignedCertificateRsa(CertificateBuilders.X500NameHolder x500Name);

    SelfSignedCertificate generateSelfSignedCertificateEcdsa(CertificateBuilders.X500NameHolder x500Name);
}

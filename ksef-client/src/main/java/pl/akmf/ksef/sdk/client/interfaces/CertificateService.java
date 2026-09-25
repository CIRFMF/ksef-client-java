package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.security.cert.X509Certificate;

public interface CertificateService {

    /**
     * Oblicza odcisk (fingerprint) certyfikatu w algorytmie SHA-256, zwracany jako ciąg znaków szesnastkowych (wielkie litery).
     *
     * @param certificate Certyfikat, dla którego liczony jest odcisk.
     * @return Odcisk SHA-256 certyfikatu w postaci szesnastkowej.
     * @throws SystemKSeFSDKException gdy certyfikat nie może zostać zakodowany lub algorytm SHA-256 jest niedostępny.
     */
    String getSha256Fingerprint(X509Certificate certificate) throws SystemKSeFSDKException;

    /**
     * Generuje samopodpisany certyfikat osobisty (kluczem RSA) wraz z parą kluczy, na podstawie danych osoby fizycznej.
     *
     * @param givenName          Imię.
     * @param surname            Nazwisko.
     * @param serialNumberPrefix Prefiks identyfikatora (np. rodzaj identyfikatora, np. PNOPL/TINPL).
     * @param serialNumber       Numer identyfikacyjny (np. PESEL lub NIP).
     * @param commonName         Nazwa pospolita (CN) certyfikatu.
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName);

    /**
     * Generuje samopodpisany certyfikat osobisty wraz z parą kluczy, na podstawie danych osoby fizycznej,
     * z wyborem algorytmu klucza.
     *
     * @param givenName          Imię.
     * @param surname            Nazwisko.
     * @param serialNumberPrefix Prefiks identyfikatora (np. rodzaj identyfikatora, np. PNOPL/TINPL).
     * @param serialNumber       Numer identyfikacyjny (np. PESEL lub NIP).
     * @param commonName         Nazwa pospolita (CN) certyfikatu.
     * @param encryptionMethod   Algorytm klucza certyfikatu (RSA albo ECDSA).
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate getPersonalCertificate(String givenName, String surname, String serialNumberPrefix, String serialNumber, String commonName, EncryptionMethod encryptionMethod);

    /**
     * Generuje samopodpisaną pieczęć firmową (kluczem RSA) wraz z parą kluczy, na podstawie danych podmiotu.
     *
     * @param organizationName       Nazwa organizacji.
     * @param organizationIdentifier Identyfikator organizacji (np. NIP).
     * @param commonName             Nazwa pospolita (CN) certyfikatu.
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName);

    /**
     * Generuje samopodpisaną pieczęć firmową wraz z parą kluczy, na podstawie danych podmiotu,
     * z wyborem algorytmu klucza.
     *
     * @param organizationName       Nazwa organizacji.
     * @param organizationIdentifier Identyfikator organizacji (np. NIP).
     * @param commonName             Nazwa pospolita (CN) certyfikatu.
     * @param encryptionMethod       Algorytm klucza certyfikatu (RSA albo ECDSA).
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate getCompanySeal(String organizationName, String organizationIdentifier, String commonName, EncryptionMethod encryptionMethod);

    /**
     * Generuje samopodpisany certyfikat RSA (2048 bitów, podpisany SHA256withRSA) dla wskazanego podmiotu.
     *
     * @param x500Name Dane podmiotu (X.500 Distinguished Name) certyfikatu.
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate generateSelfSignedCertificateRsa(CertificateBuilders.X500NameHolder x500Name);

    /**
     * Generuje samopodpisany certyfikat ECDSA (krzywa 256-bitowa, podpisany SHA256withECDSA) dla wskazanego podmiotu.
     *
     * @param x500Name Dane podmiotu (X.500 Distinguished Name) certyfikatu.
     * @return Wygenerowany samopodpisany certyfikat wraz z parą kluczy.
     */
    SelfSignedCertificate generateSelfSignedCertificateEcdsa(CertificateBuilders.X500NameHolder x500Name);
}

package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.publickey.PublicKeyCertificate;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.system.KsefIntegrationMode;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;

public interface CryptographyService {
    /**
     * Generuje nowe dane szyfrowania (losowy klucz AES-256 i wektor IV), szyfrując klucz AES
     * kluczem publicznym do szyfrowania symetrycznego pobranym z API KSeF.
     *
     * @return Nowo wygenerowane dane szyfrowania (klucz AES, IV, zaszyfrowany klucz oraz metadane szyfrowania).
     * @throws SystemKSeFSDKException gdy serwis nie został poprawnie zainicjowany lub wystąpi błąd kryptograficzny.
     */
    EncryptionData getEncryptionData() throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany token KSeF (połączony ze znacznikiem czasu) przy użyciu algorytmu RSA
     * z kluczem publicznym pobranym z API KSeF.
     *
     * @param ksefToken          Token KSeF do zaszyfrowania.
     * @param challengeTimestamp Znacznik czasu challenge'u, dołączany do tokenu przed zaszyfrowaniem.
     * @return Zaszyfrowany token KSeF (RSA).
     * @throws SystemKSeFSDKException gdy serwis nie został poprawnie zainicjowany lub wystąpi błąd kryptograficzny.
     * @deprecated użyj {@link #encryptKsefTokenUsingPublicKey(String, Instant)}, który sam dobiera algorytm klucza.
     */
    @Deprecated
    byte[] encryptKsefTokenWithRSAUsingPublicKey(String ksefToken, Instant challengeTimestamp) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany token KSeF (połączony ze znacznikiem czasu) przy użyciu algorytmu ECIES
     * (ECDH + AES-GCM) z kluczem publicznym pobranym z API KSeF.
     *
     * @param ksefToken          Token KSeF do zaszyfrowania.
     * @param challengeTimestamp Znacznik czasu challenge'u, dołączany do tokenu przed zaszyfrowaniem.
     * @return Zaszyfrowany token KSeF (ECIES).
     * @throws SystemKSeFSDKException gdy serwis nie został poprawnie zainicjowany lub wystąpi błąd kryptograficzny.
     * @deprecated użyj {@link #encryptKsefTokenUsingPublicKey(String, Instant)}, który sam dobiera algorytm klucza.
     */
    @Deprecated
    byte[] encryptKsefTokenWithECDsaUsingPublicKey(String ksefToken, Instant challengeTimestamp) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany token KSeF (połączony ze znacznikiem czasu), automatycznie dobierając
     * algorytm szyfrowania (RSA albo ECIES) na podstawie typu klucza publicznego pobranego z API KSeF.
     *
     * @param ksefToken          Token KSeF do zaszyfrowania.
     * @param challengeTimestamp Znacznik czasu challenge'u, dołączany do tokenu przed zaszyfrowaniem.
     * @return Zaszyfrowany token KSeF.
     */
    byte[] encryptKsefTokenUsingPublicKey(String ksefToken, Instant challengeTimestamp);

    /**
     * Szyfruje dowolną tablicę bajtów, automatycznie dobierając algorytm szyfrowania (RSA albo ECIES)
     * na podstawie typu klucza publicznego pobranego z API KSeF.
     *
     * @param content Dane do zaszyfrowania.
     * @return Zaszyfrowane dane.
     */
    byte[] encryptUsingPublicKey(byte[] content);

    /**
     * Zwraca zaszyfrowany content przy użyciu algorytmu RSA z publicznym kluczem.
     *
     * @param content Dane do zaszyfrowania.
     * @return Zaszyfrowane dane (RSA).
     * @throws SystemKSeFSDKException gdy serwis nie został poprawnie zainicjowany lub wystąpi błąd kryptograficzny.
     * @deprecated użyj {@link #encryptUsingPublicKey(byte[])}, który sam dobiera algorytm klucza.
     */
    @Deprecated
    byte[] encryptWithRSAUsingPublicKey(byte[] content) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany content przy użyciu algorytmu ECIes z publicznym kluczem.
     *
     * @param content Dane do zaszyfrowania.
     * @return Zaszyfrowane dane (ECIES).
     * @throws SystemKSeFSDKException gdy serwis nie został poprawnie zainicjowany lub wystąpi błąd kryptograficzny.
     * @deprecated użyj {@link #encryptUsingPublicKey(byte[])}, który sam dobiera algorytm klucza.
     */
    @Deprecated
    byte[] encryptWithECDsaUsingPublicKey(byte[] content) throws SystemKSeFSDKException;

    /**
     * Deszyfrowanie danych przy użyciu AES-256 w trybie CBC z PKCS7.
     *
     * @param encryptedPackagePart - Zaszyfrowany plik w formie tablicy bajtów.
     * @param cipherKey            - Klucz symetryczny
     * @param cipherIv             - Wektor inicjalizujący (IV) klucza symetrycznego
     * @return - Odszyfrowany plik w formie tablicy bajtów.
     */
    byte[] decryptBytesWithAes256(byte[] encryptedPackagePart, byte[] cipherKey, byte[] cipherIv);

    /**
     * Deszyfrowanie danych przy użyciu AES-256 w trybie CBC z PKCS7.
     *
     * @param encryptedPackagePart - Input stream - zaszyfrowany.
     * @param output               - Output stream - odszyfrowany.
     * @param cipherKey            - Klucz symetryczny.
     * @param cipherIv             - Wektor inicjalizujący (IV) klucza symetrycznego.
     */
    void decryptStreamBytesWithAes256(InputStream encryptedPackagePart, OutputStream output, byte[] cipherKey, byte[] cipherIv);

    /**
     * Szyfrowanie danych przy użyciu AES-256 w trybie CBC z PKCS7 paddingiem.
     *
     * @param content - Plik w formie byte array
     * @param key     - Klucz symetryczny
     * @param iv      - Wektor IV klucza symetrycznego
     * @return Zaszyfrowany plik w formie byte array
     * @throws SystemKSeFSDKException gdy wystąpi błąd kryptograficzny.
     */
    byte[] encryptBytesWithAES256(byte[] content, byte[] key, byte[] iv) throws SystemKSeFSDKException;

    /**
     * Szyfrowanie strumienia danych przy użyciu AES-256 w trybie CBC z PKCS7 paddingiem,
     * bez buforowania całej zawartości w pamięci.
     *
     * @param input  - strumień wejściowy niezaszyfrowany
     * @param output - strumień wyjściowy zaszyfrowany
     * @param key    - Klucz symetryczny
     * @param iv     - Wektor IV klucza symetrycznego
     * @throws SystemKSeFSDKException gdy wystąpi błąd kryptograficzny.
     */
    void encryptStreamWithAES256(InputStream input, OutputStream output, byte[] key, byte[] iv) throws SystemKSeFSDKException;

    /**
     * Generuje żądanie podpisania certyfikatu (CSR) kluczem RSA (2048 bitów) na podstawie przekazanych informacji o certyfikacie.
     *
     * @param certificateInfo Dane podmiotu i informacje wymagane do zbudowania żądania (CSR).
     * @return Zwraca CSR oraz klucz prywatny, oba zakodowane w formacie Base64
     * @throws SystemKSeFSDKException gdy wystąpi błąd kryptograficzny.
     */
    CsrResult generateCsrWithRsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException;

    /**
     * Generuje żądanie podpisania certyfikatu (CSR) z użyciem krzywej eliptycznej (EC) na podstawie przekazanych informacji o certyfikacie.
     *
     * @param certificateInfo Dane podmiotu i informacje wymagane do zbudowania żądania (CSR).
     * @return Zwraca CSR oraz klucz prywatny, oba zakodowane w Base64 w formacie DER
     * @throws SystemKSeFSDKException gdy wystąpi błąd kryptograficzny.
     */
    CsrResult generateCsrWithEcdsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException;

    /**
     * Zwraca metadane plik: rozmiar i hash SHA256.
     *
     * @param file - Plik w formie byte array
     * @return - FileMetadata
     * @throws SystemKSeFSDKException gdy algorytm SHA-256 jest niedostępny.
     */
    FileMetadata getMetaData(byte[] file) throws SystemKSeFSDKException;

    /**
     * Zwraca metadane pliku: rozmiar i hash SHA256 dla strumienia bez buforowania całej zawartości w pamięci.
     *
     * @param inputStream - Strumień pliku.
     * @return - FileMetadata
     * @throws SystemKSeFSDKException gdy strumień jest {@code null}, algorytm SHA-256 jest niedostępny lub wystąpi błąd odczytu.
     */
    FileMetadata getMetaData(InputStream inputStream) throws SystemKSeFSDKException;

    /**
     * Zwraca klucz publiczny w formacie PublicKey na podstawie certyfikatu w formacie PEM.
     *
     * @param certificatePem Certyfikat w formacie PEM (tekstowym).
     * @return Klucz publiczny odczytany z certyfikatu.
     * @throws SystemKSeFSDKException gdy certyfikat jest nieprawidłowy lub nie można go odczytać.
     */
    PublicKey parsePublicKeyFromCertificatePem(String certificatePem) throws SystemKSeFSDKException;

    /**
     * Zwraca klucz prywatny w formacie PrivateKey na podstawie klucza RSA zakodowanego w formacie PKCS#8 (DER).
     *
     * @param privateKey Klucz prywatny RSA zakodowany w formacie PKCS#8 (DER).
     * @return Klucz prywatny w formacie PrivateKey.
     * @throws SystemKSeFSDKException gdy klucz jest nieprawidłowy lub algorytm RSA jest niedostępny.
     */
    PrivateKey parseRsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException;

    /**
     * Zwraca klucz prywatny w formacie PrivateKey na podstawie klucza ECDSA zakodowanego w formacie PKCS#8 (DER).
     *
     * @param privateKey Klucz prywatny ECDSA zakodowany w formacie PKCS#8 (DER).
     * @return Klucz prywatny w formacie PrivateKey.
     * @throws SystemKSeFSDKException gdy klucz jest nieprawidłowy lub algorytm EC jest niedostępny.
     */
    PrivateKey parseEcdsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException;

    /**
     * Odszyfrowuje i zwraca klucz prywatny ECDSA zaszyfrowany hasłem, zapisany w formacie PEM
     * (nagłówek {@code BEGIN ENCRYPTED PRIVATE KEY}).
     *
     * @param pemBytes Zaszyfrowany klucz prywatny ECDSA w formacie PEM.
     * @param password Hasło szyfrujące klucz prywatny.
     * @return Odszyfrowany klucz prywatny.
     * @throws SystemKSeFSDKException gdy klucz nie jest zaszyfrowanym kluczem PKCS#8 lub hasło jest nieprawidłowe.
     */
    PrivateKey parseEncryptedEcdsaPrivateKeyFromPem(byte[] pemBytes, char[] password);

    /**
     * Zwraca certyfikat w formacie X509Certificate na podstawie tablicy bajtów zawierającej certyfikat (DER).
     *
     * @param certBytes Certyfikat X.509 w postaci zakodowanych bajtów (DER).
     * @return Certyfikat w formacie X509Certificate.
     * @throws CertificateException gdy certyfikat jest nieprawidłowy.
     */
    X509Certificate parseCertificateFromBytes(byte[] certBytes) throws CertificateException;

    /**
     * Zwraca certyfikat w formacie X509Certificate na podstawie certyfikatu w formacie PEM.
     *
     * @param pem Certyfikat X.509 w formacie PEM (tekstowym, z nagłówkami BEGIN/END CERTIFICATE).
     * @return Certyfikat w formacie X509Certificate.
     * @throws CertificateException gdy certyfikat jest nieprawidłowy.
     */
    X509Certificate parseCertificate(String pem) throws CertificateException;

    /**
     * Inicjuje (lub ponownie inicjuje) serwis kryptograficzny, pobierając z API KSeF aktualne
     * certyfikaty publiczne używane do szyfrowania klucza symetrycznego oraz tokenu KSeF.
     * W razie niepowodzenia serwis przechodzi w tryb {@code OFFLINE} (patrz {@link #getKsefIntegrationMode()}).
     *
     * @throws SystemKSeFSDKException gdy wystąpi nieoczekiwany błąd inicjalizacji.
     */
    void initCryptographyService();

    /**
     * Zwraca status serwisu (w razie nieudanego pobrania certyfikatów podczas inicjowania serwisu jest ustawiony na OFFLINE).
     * Możliwe jest wtedy ponowne wywyłanie usługi initCryptographyService() w celu próby inicjalizacji serwisu
     *
     * @return Aktualny tryb pracy serwisu ({@code ONLINE} albo {@code OFFLINE}).
     */
    KsefIntegrationMode getKsefIntegrationMode();

    /**
     * Zwraca powód przejścia w tryb OFFLINE.
     *
     * @return Wyjątek, który spowodował przejście w tryb OFFLINE, albo {@code null}, jeśli serwis działa poprawnie.
     */
    Exception getOfflineModeCause();

    /**
     * Certyfikat używany do szyfrowania klucza symetrycznego w formacie PEM.
     *
     * @return Certyfikat w formacie PEM albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     */
    String getSymmetricKeyEncryptionPem();

    /**
     * Certyfikat używany do szyfrowania tokenu KSeF w formacie PEM.
     *
     * @return Certyfikat w formacie PEM albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     */
    String getKsefTokenPem();

    /**
     * Certyfikat używany do szyfrowania klucza symetrycznego w formie PublicKeyCertificate.
     *
     * @return Certyfikat wraz z metadanymi albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     */
    PublicKeyCertificate getSymmetricKeyEncryption();

    /**
     * Certyfikat używany do szyfrowania tokenu KSeF w formie PublicKeyCertificate.
     *
     * @return Certyfikat wraz z metadanymi albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     */
    PublicKeyCertificate getKsefToken();

    /**
     * Certyfikat używany do szyfrowania symetrycznego klucza AES.
     *
     * @return Certyfikat w formacie X509Certificate albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     * @throws CertificateException gdy certyfikat jest nieprawidłowy.
     */
    X509Certificate getSymmetricKeyCertificate() throws CertificateException;

    /**
     * Certyfikat używany do szyfrowania tokena KSeF.
     *
     * @return Certyfikat w formacie X509Certificate albo {@code null}, jeśli serwis działa w trybie OFFLINE.
     * @throws CertificateException gdy certyfikat jest nieprawidłowy.
     */
    X509Certificate getKsefTokenCertificate() throws CertificateException;
}

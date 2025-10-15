package pl.akmf.ksef.sdk.client.interfaces;

import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.system.SystemKSeFSDKException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;

public interface CryptographyService {
    /**
     * Zwraca dane szyfrowania, w tym klucz szyfrowania, wektor IV i zaszyfrowany klucz.
     *
     * @return
     * @throws SystemKSeFSDKException
     */
    EncryptionData getEncryptionData() throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany token KSeF przy użyciu algorytmu RSA z publicznym kluczem.
     *
     * @param ksefToken
     * @param challengeTimestamp
     * @return
     * @throws SystemKSeFSDKException
     */
    byte[] encryptKsefTokenWithRSAUsingPublicKey(String ksefToken, Instant challengeTimestamp) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany token KSeF przy użyciu algorytmu ECIes z publicznym kluczem.
     *
     * @param ksefToken
     * @param challengeTimestamp
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    byte[] encryptKsefTokenWithECDsaUsingPublicKey(String ksefToken, Instant challengeTimestamp) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany content przy użyciu algorytmu RSA z publicznym kluczem.
     *
     * @param content
     * @return
     * @throws SystemKSeFSDKException
     */
    byte[] encryptWithRSAUsingPublicKey(byte[] content) throws SystemKSeFSDKException;

    /**
     * Zwraca zaszyfrowany content przy użyciu algorytmu ECIes z publicznym kluczem.
     *
     * @param content
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    byte[] encryptWithECDsaUsingPublicKey(byte[] content) throws SystemKSeFSDKException;

    /**
     * Szyfrowanie danych przy użyciu AES-256 w trybie CBC z PKCS7 paddingiem.
     *
     * @param content - Plik w formie byte array
     * @param key     - Klucz symetryczny
     * @param iv      - Wektor IV klucza symetrycznego
     * @return Zaszyfrowany plik w formie byte array
     * @throws SystemKSeFSDKException
     */
    byte[] encryptBytesWithAES256(byte[] content, byte[] key, byte[] iv) throws SystemKSeFSDKException;

    /**
     * @param input  - strumień wejściowy niezaszyfrowany
     * @param output - strumień wyjściowy zaszyfrowany
     * @param key    - Klucz symetryczny
     * @param iv     - Wektor IV klucza symetrycznego
     * @throws SystemKSeFSDKException
     */
    void encryptStreamWithAES256(InputStream input, OutputStream output, byte[] key, byte[] iv) throws SystemKSeFSDKException;

    /**
     * Generuje żądanie podpisania certyfikatu (CSR) na podstawie przekazanych informacji o certyfikacie.
     *
     * @param certificateInfo
     * @return Zwraca CSR oraz klucz prywatny, oba zakodowane w formacie Base64
     * @throws SystemKSeFSDKException
     */
    CsrResult generateCsrWithRsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException;

    /**
     * Generuje żądanie podpisania certyfikatu (CSR) z użyciem krzywej eliptycznej (EC) na podstawie przekazanych informacji o certyfikacie.
     *
     * @param certificateInfo
     * @return Zwraca CSR oraz klucz prywatny, oba zakodowane w Base64 w formacie DER
     * @throws SystemKSeFSDKException
     */
    CsrResult generateCsrWithEcdsa(CertificateEnrollmentsInfoResponse certificateInfo) throws SystemKSeFSDKException;

    /**
     * Zwraca metadane plik: rozmiar i hash SHA256.
     *
     * @param file - Plik w formie byte array
     * @return - FileMetadata
     * @throws SystemKSeFSDKException
     */
    FileMetadata getMetaData(byte[] file) throws SystemKSeFSDKException;

    /**
     * Zwraca metadane pliku: rozmiar i hash SHA256 dla strumienia bez buforowania całej zawartości w pamięci.
     *
     * @param inputStream - Strumień pliku.
     * @return - FileMetadata
     * @throws SystemKSeFSDKException
     */
    FileMetadata getMetaData(InputStream inputStream) throws SystemKSeFSDKException;

    PublicKey parsePublicKeyFromCertificatePem(String certificatePem) throws SystemKSeFSDKException;

    PrivateKey parseRsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException;

    PrivateKey parseEcdsaPrivateKeyFromPem(byte[] privateKey) throws SystemKSeFSDKException;

}

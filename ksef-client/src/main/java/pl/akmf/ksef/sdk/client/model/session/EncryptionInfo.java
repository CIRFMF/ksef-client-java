package pl.akmf.ksef.sdk.client.model.session;

/**
 * EncryptionInfo.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EncryptionInfo {

    /**
     * Klucz symetryczny o długości 32 bajtów, zaszyfrowany algorytmem RSA (Padding: OAEP z SHA-256), zakodowany w formacie Base64.
     * <p>
     * <a href="/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego">Klucz publiczny Ministerstwa Finansów</a>
     */
    private String encryptedSymmetricKey;

    /**
     * Wektor inicjalizujący (IV) o długości 16 bajtów, używany do szyfrowania symetrycznego, zakodowany w formacie Base64.
     */
    private String initializationVector;

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania.
     */
    private String publicKeyId;

    public EncryptionInfo() {
    }

    public EncryptionInfo(String encryptedSymmetricKey, String initializationVector) {
        this.encryptedSymmetricKey = encryptedSymmetricKey;
        this.initializationVector = initializationVector;
    }

    public EncryptionInfo(String encryptedSymmetricKey, String initializationVector, String publicKeyId) {
        this.encryptedSymmetricKey = encryptedSymmetricKey;
        this.initializationVector = initializationVector;
        this.publicKeyId = publicKeyId;
    }

    /**
     * Klucz symetryczny o długości 32 bajtów, zaszyfrowany algorytmem RSA (Padding: OAEP z SHA-256), zakodowany w formacie Base64.
     * <p>
     * <a href="/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego">Klucz publiczny Ministerstwa Finansów</a>
     */
    public String getEncryptedSymmetricKey() {
        return encryptedSymmetricKey;
    }

    /**
     * Klucz symetryczny o długości 32 bajtów, zaszyfrowany algorytmem RSA (Padding: OAEP z SHA-256), zakodowany w formacie Base64.
     * <p>
     * <a href="/docs/v2/index.html#tag/Certyfikaty-klucza-publicznego">Klucz publiczny Ministerstwa Finansów</a>
     */
    public void setEncryptedSymmetricKey(String encryptedSymmetricKey) {
        this.encryptedSymmetricKey = encryptedSymmetricKey;
    }

    /**
     * Wektor inicjalizujący (IV) o długości 16 bajtów, używany do szyfrowania symetrycznego, zakodowany w formacie Base64.
     */
    public String getInitializationVector() {
        return initializationVector;
    }

    /**
     * Wektor inicjalizujący (IV) o długości 16 bajtów, używany do szyfrowania symetrycznego, zakodowany w formacie Base64.
     */
    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania.
     */
    public String getPublicKeyId() {
        return publicKeyId;
    }

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania.
     */
    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }
}

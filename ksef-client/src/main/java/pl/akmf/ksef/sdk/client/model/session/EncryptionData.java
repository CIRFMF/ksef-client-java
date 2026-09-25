package pl.akmf.ksef.sdk.client.model.session;

import java.util.Objects;

/**
 * Wewnętrzny nośnik danych szyfrowania klienta SDK: klucz i wektor inicjujący AES
 * użyte do zaszyfrowania przesyłanych faktur, zaszyfrowany klucz AES oraz towarzyszące
 * mu {@link EncryptionInfo}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class EncryptionData {

    private final byte[] cipherKey;

    private final byte[] cipherIv;

    private final String encryptedCipherKey;

    private final EncryptionInfo encryptionInfo;

    public EncryptionData(byte[] cipherKey, byte[] cipherIv, String encryptedCipherKey, EncryptionInfo encryptionInfo) {
        if (cipherKey == null) {
            throw new IllegalArgumentException("cipherKey cannot be null");
        }
        if (cipherIv == null) {
            throw new IllegalArgumentException("cipherIv cannot be null");
        }
        if (encryptedCipherKey == null) {
            throw new IllegalArgumentException("encryptedCipherKey cannot be null");
        }
        if (encryptionInfo == null) {
            throw new IllegalArgumentException("encryptionInfo cannot be null");
        }
        this.cipherKey = cipherKey.clone();
        this.cipherIv = cipherIv.clone();
        this.encryptedCipherKey = encryptedCipherKey;
        this.encryptionInfo = encryptionInfo;
    }

    public byte[] cipherKey() {
        return cipherKey;
    }

    public byte[] cipherIv() {
        return cipherIv;
    }

    public String encryptedCipherKey() {
        return encryptedCipherKey;
    }

    public EncryptionInfo encryptionInfo() {
        return encryptionInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (EncryptionData) obj;
        return Objects.equals(this.cipherKey, that.cipherKey) && Objects.equals(this.cipherIv, that.cipherIv) && Objects.equals(this.encryptedCipherKey, that.encryptedCipherKey) && Objects.equals(this.encryptionInfo, that.encryptionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cipherKey, cipherIv, encryptedCipherKey, encryptionInfo);
    }

    @Override
    public String toString() {
        return "EncryptionData[" + "cipherKey=" + cipherKey + ", " + "cipherIv=" + cipherIv + ", " + "encryptedCipherKey=" + encryptedCipherKey + ", " + "encryptionInfo=" + encryptionInfo + ']';
    }
}

package pl.akmf.ksef.sdk.client.model.session;

public class EncryptionInfo {
    private String encryptedSymmetricKey;
    private String initializationVector;
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

    public String getEncryptedSymmetricKey() {
        return encryptedSymmetricKey;
    }

    public void setEncryptedSymmetricKey(String encryptedSymmetricKey) {
        this.encryptedSymmetricKey = encryptedSymmetricKey;
    }

    public String getInitializationVector() {
        return initializationVector;
    }

    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }
}


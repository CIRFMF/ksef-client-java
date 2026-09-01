package pl.akmf.ksef.sdk.api.builders.batch;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchFileInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchFilePartInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.CompressionType;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.util.RegexPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpenBatchSessionRequestBuilder {

    private FormCode formCode;
    private final List<BatchFilePartInfo> parts = new ArrayList<>();
    private long batchFileSize;
    private String batchFileHash = "";
    private final EncryptionInfo encryption = new EncryptionInfo();
    private boolean offlineMode = false;
    private CompressionType compressionType;

    // Ograniczenia wynikają bezpośrednio ze schematów OpenAPI BatchFileInfo i BatchFilePartInfo.
    private static final long openApiMinimumFileSizeInBytes = 1L;
    private static final long openApiMaximumBatchFileSizeInBytes = 5_000_000_000L;
    private static final int openApiMinimumBatchFilePartOrdinalNumber = 1;
    private static final int openApiMinimumBatchFileParts = 1;
    private static final int openApiMaximumBatchFileParts = 50;

    private OpenBatchSessionRequestBuilder() {
    }

    public static OpenBatchSessionRequestBuilder create() {
        return new OpenBatchSessionRequestBuilder();
    }

    public OpenBatchSessionRequestBuilder withFormCode(SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value) {
        if (Objects.isNull(systemCode) || Objects.isNull(schemaVersion) || Objects.isNull(value)) {
            throw new IllegalArgumentException("FormCode parameters cannot be null or empty.");
        }

        this.formCode = new FormCode();
        this.formCode.setSystemCode(systemCode);
        this.formCode.setValue(value);
        this.formCode.setSchemaVersion(schemaVersion);
        return this;
    }

    public OpenBatchSessionRequestBuilder withBatchFile(long fileSize, String fileHash) {
        if (fileSize < openApiMinimumFileSizeInBytes || fileSize > openApiMaximumBatchFileSizeInBytes) {
            throw new IllegalArgumentException("Incorrect BatchFile parameters." +
                    " OpenAPI schema for BatchFileInfo requires the fileSize is in range "
                    + openApiMinimumFileSizeInBytes + ".." + openApiMaximumBatchFileSizeInBytes + " bytes.");
        }

        if (isNullOrBlank(fileHash) || !RegexPatterns.Sha256Base64Pattern.isMatch(fileHash)) {
            throw new IllegalArgumentException("OpenAPI Schema for BatchFileInfo requires the fileHash is compatible with Sha256HashBase64.");
        }

        this.batchFileSize = fileSize;
        this.batchFileHash = fileHash;
        this.compressionType = CompressionType.TarGz; // Domyślnie przyjmuje typ TarGz
        return this;
    }

    /**
     * Ustawia podstawowe informacje o pliku wsadowym wraz z typem kompresji.
     *
     * @param fileSize        Rozmiar pliku wsadowego w bajtach.
     * @param fileHash        Skrót kryptograficzny całego pliku wsadowego.
     * @param compressionType Typ kompresji pliku wsadowego (np. Zip lub TarGz).
     * @return Interfejs do dodawania części pliku wsadowego.
     */
    public OpenBatchSessionRequestBuilder withBatchFile(long fileSize, String fileHash, CompressionType compressionType) {
        withBatchFile(fileSize, fileHash);
        this.compressionType = compressionType;
        return this;
    }

    public OpenBatchSessionRequestBuilder withOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
        return this;
    }

    public OpenBatchSessionRequestBuilder addBatchFilePart(int ordinalNumber, long fileSize, String fileHash) {
        if (ordinalNumber < openApiMinimumBatchFilePartOrdinalNumber || fileSize < openApiMinimumFileSizeInBytes) {
            throw new IllegalArgumentException(
                    "Incorrect BatchFilePart parameters. OpenAPI schema for BatchFilePartInfo requires " +
                            "ordinalNumber >= " + openApiMinimumBatchFilePartOrdinalNumber + " and " +
                            "fileSize >= " + openApiMinimumFileSizeInBytes + ".");
        }

        if (isNullOrBlank(fileHash) || !RegexPatterns.Sha256Base64Pattern.isMatch(fileHash)) {
            throw new IllegalArgumentException(
                    "OpenAPI schema for BatchFilePartInfo requires the fileHash is compatible with Sha256HashBase64.");
        }

        if (this.parts.size() >= openApiMaximumBatchFileParts) {
            throw new IllegalArgumentException("OpenAPI schema for BatchFileInfo allows max " + openApiMaximumBatchFileParts + " for file part.");
        }

        BatchFilePartInfo batchFilePartInfo = new BatchFilePartInfo();
        batchFilePartInfo.setOrdinalNumber(ordinalNumber);
        batchFilePartInfo.setFileSize(fileSize);
        batchFilePartInfo.setFileHash(fileHash);
        this.parts.add(batchFilePartInfo);
        return this;
    }


    public OpenBatchSessionRequestBuilder endBatchFile() {
        if (isNullOrBlank(batchFileHash)) {
            throw new IllegalStateException("BatchFile hash must be set.");
        }
        return this;
    }

    public OpenBatchSessionRequestBuilder withEncryption(String encryptedSymmetricKey, String initializationVector) {
        if (isNullOrBlank(encryptedSymmetricKey) || isNullOrBlank(initializationVector)) {
            throw new IllegalArgumentException("Encryption parameters cannot be null or empty.");
        }

        this.encryption.setEncryptedSymmetricKey(encryptedSymmetricKey);
        this.encryption.setInitializationVector(initializationVector);
        return this;
    }

    public OpenBatchSessionRequestBuilder withEncryption(String encryptedSymmetricKey, String initializationVector, String publicKeyId) {
        withEncryption(encryptedSymmetricKey, initializationVector);
        this.encryption.setPublicKeyId(publicKeyId);
        return this;
    }

    public OpenBatchSessionRequest build() {
        if (formCode == null) throw new IllegalStateException("FormCode is required.");
        if (isNullOrBlank(encryption.getEncryptedSymmetricKey()) || isNullOrBlank(encryption.getInitializationVector())) {
            throw new IllegalStateException("Encryption configuration is incomplete.");
        }
        if (this.parts.size() < openApiMinimumBatchFileParts) {
            throw new IllegalArgumentException("OpenAPI schema BatchFileInfo requires at least " + openApiMinimumBatchFileParts + " file part.");
        }

        BatchFileInfo batchFile = new BatchFileInfo();
        batchFile.setFileSize(batchFileSize);
        batchFile.setFileHash(batchFileHash);
        batchFile.setFileParts(parts);
        batchFile.setCompressionType(compressionType);
        OpenBatchSessionRequest openBatchSessionRequest = new OpenBatchSessionRequest();
        openBatchSessionRequest.setFormCode(formCode);
        openBatchSessionRequest.setBatchFile(batchFile);
        openBatchSessionRequest.setEncryption(encryption);
        openBatchSessionRequest.setOfflineMode(offlineMode);

        return openBatchSessionRequest;
    }

    private boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}

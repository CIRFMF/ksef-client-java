package pl.akmf.ksef.sdk.api.builders.batch;

import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchFileInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchFilePartInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;

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
        if (fileSize < 0 || isNullOrBlank(fileHash)) {
            throw new IllegalArgumentException("BatchFile parameters are invalid.");
        }

        this.batchFileSize = fileSize;
        this.batchFileHash = fileHash;
        return this;
    }

    public OpenBatchSessionRequestBuilder withOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
        return this;
    }

    public OpenBatchSessionRequestBuilder addBatchFilePart(int ordinalNumber, String fileName, long fileSize, String fileHash) {
        if (ordinalNumber < 0 || isNullOrBlank(fileName) || fileSize < 0 || isNullOrBlank(fileHash)) {
            throw new IllegalArgumentException("BatchFilePart parameters are invalid.");
        }

        BatchFilePartInfo batchFilePartInfo = new BatchFilePartInfo();
        batchFilePartInfo.setOrdinalNumber(ordinalNumber);
        batchFilePartInfo.setFileName(fileName);
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

    public OpenBatchSessionRequest build() {
        if (formCode == null) throw new IllegalStateException("FormCode is required.");
        if (isNullOrBlank(encryption.getEncryptedSymmetricKey()) || isNullOrBlank(encryption.getInitializationVector())) {
            throw new IllegalStateException("Encryption configuration is incomplete.");
        }

        BatchFileInfo batchFile = new BatchFileInfo();
        batchFile.setFileSize(batchFileSize);
        batchFile.setFileHash(batchFileHash);
        batchFile.setFileParts(parts);
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

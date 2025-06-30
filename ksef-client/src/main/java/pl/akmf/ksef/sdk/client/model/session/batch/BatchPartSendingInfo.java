package pl.akmf.ksef.sdk.client.model.session.batch;

import pl.akmf.ksef.sdk.client.model.session.FileMetadata;

public class BatchPartSendingInfo {
    private byte[] data;
    private FileMetadata metadata;
    private int ordinalNumber;

    public BatchPartSendingInfo() {
    }

    public BatchPartSendingInfo(byte[] data, FileMetadata metadata, int ordinalNumber) {
        this.data = data;
        this.metadata = metadata;
        this.ordinalNumber = ordinalNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FileMetadata metadata) {
        this.metadata = metadata;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}

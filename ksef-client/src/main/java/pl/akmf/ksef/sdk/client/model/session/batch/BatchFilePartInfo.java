package pl.akmf.ksef.sdk.client.model.session.batch;

public class BatchFilePartInfo {
    private int ordinalNumber;
    private long fileSize;
    private String fileHash;

    public BatchFilePartInfo() {

    }

    public BatchFilePartInfo(int ordinalNumber, long fileSize, String fileHash) {
        this.ordinalNumber = ordinalNumber;
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}

package pl.akmf.ksef.sdk.client.model.session.batch;

public class BatchFilePartInfo {
    // Numer porządkowy części w pliku wsadowym. Minimum według OpenAPI to 1.
    private int ordinalNumber;
    // Rozmiar części pliku w bajtach. Minimum według OpenAPI to 1.
    private long fileSize;
    // Skrót kryptograficzny części pliku zgodny ze schematem OpenAPI
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

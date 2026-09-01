package pl.akmf.ksef.sdk.client.model.session.batch;

import java.util.List;

// Zawiera informacje o pliku wsadowym przekazywanym w żądaniu otwarcia sesji wsadowej.
public class BatchFileInfo {
    // Rozmiar całego pliku wsadowego w bajtach. Schamat dopuszcza zakres 1–5 000 000 000.
    private long fileSize;
    // Skrót kryptograficzny całego pliku wsadowego.
    private String fileHash;
    // Lista części pliku wsadowego. Schemat dopuszcza od 1 do 50 części.
    private List<BatchFilePartInfo> fileParts;
    // Typ kompresji pliku wsadowego.
    // Gdy wartość nie została podana, pozostaje null dla zachowania kompatybilności wstecznej.
    private CompressionType compressionType;

    public BatchFileInfo() {
    }


    public BatchFileInfo(long fileSize, String fileHash, List<BatchFilePartInfo> fileParts) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
        this.fileParts = fileParts;
    }

    public BatchFileInfo(long fileSize, String fileHash, List<BatchFilePartInfo> fileParts, CompressionType compressionType) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
        this.fileParts = fileParts;
        this.compressionType = compressionType;
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

    public List<BatchFilePartInfo> getFileParts() {
        return fileParts;
    }

    public void setFileParts(List<BatchFilePartInfo> fileParts) {
        this.fileParts = fileParts;
    }

    public CompressionType getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(CompressionType compressionType) {
        this.compressionType = compressionType;
    }
}

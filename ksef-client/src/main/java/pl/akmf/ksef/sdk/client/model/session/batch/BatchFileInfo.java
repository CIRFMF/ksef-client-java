package pl.akmf.ksef.sdk.client.model.session.batch;

import java.util.List;

/**
 * BatchFileInfo.
 *
 * Zawiera informacje o pliku wsadowym przekazywanym w żądaniu otwarcia sesji wsadowej.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BatchFileInfo {

    /**
     * Rozmiar pliku paczki w bajtach. Maksymalny rozmiar paczki to 5GB.
     */
    private long fileSize;

    /**
     * Skrót SHA256 pliku paczki, zakodowany w formacie Base64.
     */
    private String fileHash;

    /**
     * Informacje o częściach pliku paczki. Maksymalna liczba części to 50. Maksymalny dozwolony rozmiar części przed zaszyfrowaniem to 100MB.
     */
    private List<BatchFilePartInfo> fileParts;

    /**
     * Typ kompresji pliku paczki.
     * Gdy wartość nie została podana, pozostaje null dla zachowania kompatybilności wstecznej.
     */
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

    /**
     * Rozmiar pliku paczki w bajtach. Maksymalny rozmiar paczki to 5GB.
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Rozmiar pliku paczki w bajtach. Maksymalny rozmiar paczki to 5GB.
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Skrót SHA256 pliku paczki, zakodowany w formacie Base64.
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * Skrót SHA256 pliku paczki, zakodowany w formacie Base64.
     */
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    /**
     * Informacje o częściach pliku paczki. Maksymalna liczba części to 50. Maksymalny dozwolony rozmiar części przed zaszyfrowaniem to 100MB.
     */
    public List<BatchFilePartInfo> getFileParts() {
        return fileParts;
    }

    /**
     * Informacje o częściach pliku paczki. Maksymalna liczba części to 50. Maksymalny dozwolony rozmiar części przed zaszyfrowaniem to 100MB.
     */
    public void setFileParts(List<BatchFilePartInfo> fileParts) {
        this.fileParts = fileParts;
    }

    /**
     * Typ kompresji pliku paczki.
     * Gdy wartość nie została podana, pozostaje null dla zachowania kompatybilności wstecznej.
     */
    public CompressionType getCompressionType() {
        return compressionType;
    }

    /**
     * Typ kompresji pliku paczki.
     * Gdy wartość nie została podana, pozostaje null dla zachowania kompatybilności wstecznej.
     */
    public void setCompressionType(CompressionType compressionType) {
        this.compressionType = compressionType;
    }
}

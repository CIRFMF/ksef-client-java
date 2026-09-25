package pl.akmf.ksef.sdk.client.model.session.batch;

/**
 * BatchFilePartInfo.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class BatchFilePartInfo {

    /**
     * Numer porządkowy części w pliku paczki. Minimum według OpenAPI to 1.
     */
    private int ordinalNumber;

    /**
     * Rozmiar zaszyfrowanej części pliku paczki w bajtach.
     */
    private long fileSize;

    /**
     * Skrót SHA256 zaszyfrowanej części pliku paczki, zakodowany w formacie Base64.
     */
    private String fileHash;

    public BatchFilePartInfo() {
    }

    public BatchFilePartInfo(int ordinalNumber, long fileSize, String fileHash) {
        this.ordinalNumber = ordinalNumber;
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }

    /**
     * Numer porządkowy części w pliku paczki. Minimum według OpenAPI to 1.
     */
    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * Numer porządkowy części w pliku paczki. Minimum według OpenAPI to 1.
     */
    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    /**
     * Rozmiar zaszyfrowanej części pliku paczki w bajtach.
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Rozmiar zaszyfrowanej części pliku paczki w bajtach.
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Skrót SHA256 zaszyfrowanej części pliku paczki, zakodowany w formacie Base64.
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * Skrót SHA256 zaszyfrowanej części pliku paczki, zakodowany w formacie Base64.
     */
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}

package pl.akmf.ksef.sdk.client.model.session;

/**
 * Metadane pliku (skrót SHA i rozmiar) liczone lokalnie przez klienta SDK przed
 * wysyłką faktury/paczki wsadowej.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class FileMetadata {

    private String hashSHA;

    private Long fileSize;

    public FileMetadata() {
    }

    public FileMetadata(int fileSize, String hashSHA) {
        this.fileSize = (long) fileSize;
        this.hashSHA = hashSHA;
    }

    public String getHashSHA() {
        return hashSHA;
    }

    public void setHashSHA(String hashSHA) {
        this.hashSHA = hashSHA;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}

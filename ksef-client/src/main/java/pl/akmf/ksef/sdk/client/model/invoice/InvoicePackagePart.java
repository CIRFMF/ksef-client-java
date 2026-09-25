package pl.akmf.ksef.sdk.client.model.invoice;

import java.net.URI;
import java.time.OffsetDateTime;

/**
 * InvoicePackagePart.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoicePackagePart {

    /**
     * Numer sekwencyjny pliku części paczki.
     */
    private Integer ordinalNumber;

    /**
     * Nazwa pliku części paczki.
     */
    private String partName;

    /**
     * Metoda HTTP, której należy użyć przy pobieraniu pliku.
     */
    private String method;

    /**
     * Adres URL, pod który należy wysłać żądanie pobrania części paczki. Link jest generowany dynamicznie w momencie odpytania o status operacji eksportu. Nie podlega limitom API i nie wymaga przesyłania tokenu dostępowego przy pobraniu.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    private URI url;

    /**
     * Rozmiar części paczki w bajtach.
     */
    private int partSize;

    /**
     * Skrót SHA256 pliku części paczki, zakodowany w formacie Base64.
     */
    private String partHash;

    /**
     * Rozmiar zaszyfrowanej części paczki w bajtach.
     */
    private int encryptedPartSize;

    /**
     * Skrót SHA256 zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    private String encryptedPartHash;

    /**
     * Data i godzina wygaśnięcia linku umożliwiającego pobranie części paczki. Po upływie tego momentu link przestaje być aktywny.
     */
    private OffsetDateTime expirationDate;

    public InvoicePackagePart() {
    }

    /**
     * Numer sekwencyjny pliku części paczki.
     */
    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * Numer sekwencyjny pliku części paczki.
     */
    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    /**
     * Nazwa pliku części paczki.
     */
    public String getPartName() {
        return partName;
    }

    /**
     * Nazwa pliku części paczki.
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * Metoda HTTP, której należy użyć przy pobieraniu pliku.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Metoda HTTP, której należy użyć przy pobieraniu pliku.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Adres URL, pod który należy wysłać żądanie pobrania części paczki. Link jest generowany dynamicznie w momencie odpytania o status operacji eksportu. Nie podlega limitom API i nie wymaga przesyłania tokenu dostępowego przy pobraniu.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    public URI getUrl() {
        return url;
    }

    /**
     * Adres URL, pod który należy wysłać żądanie pobrania części paczki. Link jest generowany dynamicznie w momencie odpytania o status operacji eksportu. Nie podlega limitom API i nie wymaga przesyłania tokenu dostępowego przy pobraniu.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    public void setUrl(URI url) {
        this.url = url;
    }

    /**
     * Rozmiar części paczki w bajtach.
     */
    public int getPartSize() {
        return partSize;
    }

    /**
     * Rozmiar części paczki w bajtach.
     */
    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    /**
     * Skrót SHA256 pliku części paczki, zakodowany w formacie Base64.
     */
    public String getPartHash() {
        return partHash;
    }

    /**
     * Skrót SHA256 pliku części paczki, zakodowany w formacie Base64.
     */
    public void setPartHash(String partHash) {
        this.partHash = partHash;
    }

    /**
     * Rozmiar zaszyfrowanej części paczki w bajtach.
     */
    public int getEncryptedPartSize() {
        return encryptedPartSize;
    }

    /**
     * Rozmiar zaszyfrowanej części paczki w bajtach.
     */
    public void setEncryptedPartSize(int encryptedPartSize) {
        this.encryptedPartSize = encryptedPartSize;
    }

    /**
     * Skrót SHA256 zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    public String getEncryptedPartHash() {
        return encryptedPartHash;
    }

    /**
     * Skrót SHA256 zaszyfrowanej części paczki, zakodowany w formacie Base64.
     */
    public void setEncryptedPartHash(String encryptedPartHash) {
        this.encryptedPartHash = encryptedPartHash;
    }

    /**
     * Data i godzina wygaśnięcia linku umożliwiającego pobranie części paczki. Po upływie tego momentu link przestaje być aktywny.
     */
    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Data i godzina wygaśnięcia linku umożliwiającego pobranie części paczki. Po upływie tego momentu link przestaje być aktywny.
     */
    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}

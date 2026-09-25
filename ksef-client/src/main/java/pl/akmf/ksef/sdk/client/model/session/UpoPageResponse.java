package pl.akmf.ksef.sdk.client.model.session;

import java.net.URI;
import java.time.OffsetDateTime;

/**
 * UpoPageResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class UpoPageResponse {

    /**
     * Numer referencyjny strony UPO.
     */
    private String referenceNumber;

    /**
     * Adres do pobrania strony UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code DownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    private URI downloadUrl;

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code DownloadUrl} nie będzie już aktywny.
     */
    private OffsetDateTime downloadUrlExpirationDate;

    public UpoPageResponse() {
    }

    public UpoPageResponse(String referenceNumber, URI downloadUrl) {
        this.referenceNumber = referenceNumber;
        this.downloadUrl = downloadUrl;
    }

    /**
     * Numer referencyjny strony UPO.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny strony UPO.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Adres do pobrania strony UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code DownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    public URI getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Adres do pobrania strony UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code DownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    public void setDownloadUrl(URI downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code DownloadUrl} nie będzie już aktywny.
     */
    public OffsetDateTime getDownloadUrlExpirationDate() {
        return downloadUrlExpirationDate;
    }

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code DownloadUrl} nie będzie już aktywny.
     */
    public void setDownloadUrlExpirationDate(OffsetDateTime downloadUrlExpirationDate) {
        this.downloadUrlExpirationDate = downloadUrlExpirationDate;
    }
}

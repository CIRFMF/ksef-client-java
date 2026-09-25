package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicingMode;
import java.time.OffsetDateTime;

/**
 * SessionInvoiceStatusResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SessionInvoiceStatusResponse {

    /**
     * Numer sekwencyjny faktury w ramach sesji.
     */
    private Integer ordinalNumber;

    /**
     * Numer faktury.
     */
    private String invoiceNumber;

    /**
     * Numer KSeF.
     */
    private String ksefNumber;

    /**
     * Numer referencyjny faktury.
     */
    private String referenceNumber;

    /**
     * Skrót SHA256 faktury, zakodowany w formacie Base64.
     */
    private String invoiceHash;

    /**
     * Nazwa pliku faktury (zwracana dla faktur wysyłanych wsadowo).
     */
    private String invoiceFileName;

    /**
     * Data przyjęcia faktury w systemie KSeF (do dalszego przetwarzania).
     */
    private OffsetDateTime invoicingDate;

    /**
     * Data nadania numeru KSeF.
     */
    private OffsetDateTime acquisitionDate;

    /**
     * Status faktury.
     */
    private StatusInfo status;

    /**
     * Data trwałego zapisu faktury w repozytorium KSeF. Wartość uzupełniana asynchronicznie w momencie trwałego zapisu; zawsze późniejsza niż &lt;b&gt;acquisitionDate&lt;/b&gt;. Podczas sprawdzania statusu może być jeszcze niedostępna.
     */
    private OffsetDateTime permanentStorageDate;

    /**
     * Adres do pobrania UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code UpoDownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    private String upoDownloadUrl;

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code UpoDownloadUrl} nie będzie już aktywny.
     */
    private String upoDownloadUrlExpirationDate;

    /**
     * Tryb fakturowania (online/offline).
     */
    private InvoicingMode invoicingMode;

    public SessionInvoiceStatusResponse() {
    }

    /**
     * Numer sekwencyjny faktury w ramach sesji.
     */
    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * Numer sekwencyjny faktury w ramach sesji.
     */
    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    /**
     * Numer faktury.
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Numer faktury.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Numer KSeF.
     */
    public String getKsefNumber() {
        return ksefNumber;
    }

    /**
     * Numer KSeF.
     */
    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    /**
     * Numer referencyjny faktury.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny faktury.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Skrót SHA256 faktury, zakodowany w formacie Base64.
     */
    public String getInvoiceHash() {
        return invoiceHash;
    }

    /**
     * Skrót SHA256 faktury, zakodowany w formacie Base64.
     */
    public void setInvoiceHash(String invoiceHash) {
        this.invoiceHash = invoiceHash;
    }

    /**
     * Nazwa pliku faktury (zwracana dla faktur wysyłanych wsadowo).
     */
    public String getInvoiceFileName() {
        return invoiceFileName;
    }

    /**
     * Nazwa pliku faktury (zwracana dla faktur wysyłanych wsadowo).
     */
    public void setInvoiceFileName(String invoiceFileName) {
        this.invoiceFileName = invoiceFileName;
    }

    /**
     * Data przyjęcia faktury w systemie KSeF (do dalszego przetwarzania).
     */
    public OffsetDateTime getInvoicingDate() {
        return invoicingDate;
    }

    /**
     * Data przyjęcia faktury w systemie KSeF (do dalszego przetwarzania).
     */
    public void setInvoicingDate(OffsetDateTime invoicingDate) {
        this.invoicingDate = invoicingDate;
    }

    /**
     * Data nadania numeru KSeF.
     */
    public OffsetDateTime getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Data nadania numeru KSeF.
     */
    public void setAcquisitionDate(OffsetDateTime acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Status faktury.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Status faktury.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Data trwałego zapisu faktury w repozytorium KSeF. Wartość uzupełniana asynchronicznie w momencie trwałego zapisu; zawsze późniejsza niż &lt;b&gt;acquisitionDate&lt;/b&gt;. Podczas sprawdzania statusu może być jeszcze niedostępna.
     */
    public OffsetDateTime getPermanentStorageDate() {
        return permanentStorageDate;
    }

    /**
     * Data trwałego zapisu faktury w repozytorium KSeF. Wartość uzupełniana asynchronicznie w momencie trwałego zapisu; zawsze późniejsza niż &lt;b&gt;acquisitionDate&lt;/b&gt;. Podczas sprawdzania statusu może być jeszcze niedostępna.
     */
    public void setPermanentStorageDate(OffsetDateTime permanentStorageDate) {
        this.permanentStorageDate = permanentStorageDate;
    }

    /**
     * Adres do pobrania UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code UpoDownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    public String getUpoDownloadUrl() {
        return upoDownloadUrl;
    }

    /**
     * Adres do pobrania UPO. Link generowany jest przy każdym odpytaniu o status. Dostęp odbywa się metodą {@code HTTP GET} i &lt;b&gt;nie należy&lt;/b&gt; wysyłać tokenu dostępowego. Link nie podlega limitom API i wygasa po określonym czasie w {@code UpoDownloadUrlExpirationDate}.
     * <p>
     * Odpowiedź HTTP zawiera dodatkowe nagłówki: - {@code x-ms-meta-hash} – skrót SHA-256 dokumentu UPO, zakodowany w formacie Base64.
     */
    public void setUpoDownloadUrl(String upoDownloadUrl) {
        this.upoDownloadUrl = upoDownloadUrl;
    }

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code UpoDownloadUrl} nie będzie już aktywny.
     */
    public String getUpoDownloadUrlExpirationDate() {
        return upoDownloadUrlExpirationDate;
    }

    /**
     * Data i godzina wygaśnięcia adresu. Po tej dacie link {@code UpoDownloadUrl} nie będzie już aktywny.
     */
    public void setUpoDownloadUrlExpirationDate(String upoDownloadUrlExpirationDate) {
        this.upoDownloadUrlExpirationDate = upoDownloadUrlExpirationDate;
    }

    /**
     * Tryb fakturowania (online/offline).
     */
    public InvoicingMode getInvoicingMode() {
        return invoicingMode;
    }

    /**
     * Tryb fakturowania (online/offline).
     */
    public void setInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
    }
}

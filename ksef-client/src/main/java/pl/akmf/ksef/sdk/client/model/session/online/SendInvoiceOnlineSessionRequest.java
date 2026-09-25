package pl.akmf.ksef.sdk.client.model.session.online;

/**
 * SendInvoiceOnlineSessionRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SendInvoiceRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SendInvoiceOnlineSessionRequest {

    /**
     * Skrót SHA256 oryginalnej faktury, zakodowany w formacie Base64.
     */
    private String invoiceHash;

    /**
     * Rozmiar oryginalnej faktury w bajtach. Maksymalny rozmiar zależy od limitów ustawionych dla uwierzytelnionego kontekstu.
     */
    private long invoiceSize;

    /**
     * Skrót SHA256 zaszyfrowanej faktury, zakodowany w formacie Base64.
     */
    private String encryptedInvoiceHash;

    /**
     * Rozmiar zaszyfrowanej faktury w bajtach.
     */
    private long encryptedInvoiceSize;

    /**
     * Faktura zaszyfrowana algorytmem AES-256-CBC z dopełnianiem PKCS#7 (kluczem przekazanym przy otwarciu sesji), zakodowana w formacie Base64.
     */
    private String encryptedInvoiceContent;

    /**
     * Określa, czy podatnik deklaruje tryb fakturowania "offline" dla przesyłanego dokumentu.
     */
    private boolean offlineMode = false;

    /**
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64. Wymagany przy wysyłaniu korekty technicznej faktury.
     */
    private String hashOfCorrectedInvoice;

    /**
     * Skrót SHA256 oryginalnej faktury, zakodowany w formacie Base64.
     */
    public String getInvoiceHash() {
        return invoiceHash;
    }

    /**
     * Skrót SHA256 oryginalnej faktury, zakodowany w formacie Base64.
     */
    public void setInvoiceHash(String invoiceHash) {
        this.invoiceHash = invoiceHash;
    }

    /**
     * Rozmiar oryginalnej faktury w bajtach. Maksymalny rozmiar zależy od limitów ustawionych dla uwierzytelnionego kontekstu.
     */
    public long getInvoiceSize() {
        return invoiceSize;
    }

    /**
     * Rozmiar oryginalnej faktury w bajtach. Maksymalny rozmiar zależy od limitów ustawionych dla uwierzytelnionego kontekstu.
     */
    public void setInvoiceSize(long invoiceSize) {
        this.invoiceSize = invoiceSize;
    }

    /**
     * Skrót SHA256 zaszyfrowanej faktury, zakodowany w formacie Base64.
     */
    public String getEncryptedInvoiceHash() {
        return encryptedInvoiceHash;
    }

    /**
     * Skrót SHA256 zaszyfrowanej faktury, zakodowany w formacie Base64.
     */
    public void setEncryptedInvoiceHash(String encryptedInvoiceHash) {
        this.encryptedInvoiceHash = encryptedInvoiceHash;
    }

    /**
     * Rozmiar zaszyfrowanej faktury w bajtach.
     */
    public long getEncryptedInvoiceSize() {
        return encryptedInvoiceSize;
    }

    /**
     * Rozmiar zaszyfrowanej faktury w bajtach.
     */
    public void setEncryptedInvoiceSize(long encryptedInvoiceSize) {
        this.encryptedInvoiceSize = encryptedInvoiceSize;
    }

    /**
     * Faktura zaszyfrowana algorytmem AES-256-CBC z dopełnianiem PKCS#7 (kluczem przekazanym przy otwarciu sesji), zakodowana w formacie Base64.
     */
    public String getEncryptedInvoiceContent() {
        return encryptedInvoiceContent;
    }

    /**
     * Faktura zaszyfrowana algorytmem AES-256-CBC z dopełnianiem PKCS#7 (kluczem przekazanym przy otwarciu sesji), zakodowana w formacie Base64.
     */
    public void setEncryptedInvoiceContent(String encryptedInvoiceContent) {
        this.encryptedInvoiceContent = encryptedInvoiceContent;
    }

    public boolean isOfflineMode() {
        return offlineMode;
    }

    /**
     * Określa, czy podatnik deklaruje tryb fakturowania "offline" dla przesyłanego dokumentu.
     */
    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }

    /**
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64. Wymagany przy wysyłaniu korekty technicznej faktury.
     */
    public String getHashOfCorrectedInvoice() {
        return hashOfCorrectedInvoice;
    }

    /**
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64. Wymagany przy wysyłaniu korekty technicznej faktury.
     */
    public void setHashOfCorrectedInvoice(String hashOfCorrectedInvoice) {
        this.hashOfCorrectedInvoice = hashOfCorrectedInvoice;
    }
}

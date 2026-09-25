package pl.akmf.ksef.sdk.client.model.invoice;

import java.util.List;

/**
 * InvoiceExportFilters.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoiceQueryFilters}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceExportFilters {

    /**
     * Typ podmiotu, którego dotyczą kryteria filtrowania metadanych faktur. Określa kontekst, w jakim przeszukiwane są dane.
     */
    private InvoiceQuerySubjectType subjectType;

    /**
     * Typ i zakres dat, według którego filtrowane są faktury. Maksymalny dozwolony okres wynosi 100 dni w strefie UTC
     * <p>
     * Format daty: * Daty muszą być przekazane w formacie ISO 8601, np. {@code yyyy-MM-ddTHH:mm:ss}. * Dopuszczalne są następujące warianty: - z sufiksem {@code Z} (czas UTC), - z jawnym offsetem, np. {@code +01:00}, {@code +03:00}
     */
    private InvoiceQueryDateRange dateRange;

    /**
     * Numer KSeF faktury (exact match).
     */
    private String ksefNumber;

    /**
     * Numer faktury nadany przez wystawcę (exact match).
     */
    private String invoiceNumber;

    /**
     * Filtr kwotowy – brutto, netto lub VAT (z wartością).
     */
    private InvoiceQueryAmount amount;

    /**
     * Nip sprzedawcy (exact match).
     */
    private String sellerNip;

    /**
     * Identyfikator nabywcy.
     */
    private InvoiceBuyerIdentifier buyerIdentifier;

    /**
     * Kody walut.
     */
    private List<CurrencyCode> currencyCodes;

    /**
     * Tryb wystawienia faktury: online lub offline.
     */
    private InvoicingMode invoicingMode;

    /**
     * Czy faktura została wystawiona w trybie samofakturowania.
     */
    private Boolean isSelfInvoicing;

    /**
     * Typ dokumentu.
     */
    private InvoiceFormType formType;

    /**
     * Rodzaje faktur.
     */
    private List<InvoiceMetadataInvoiceType> invoiceTypes;

    /**
     * Czy faktura ma załącznik.
     */
    private Boolean hasAttachment;

    public InvoiceExportFilters() {
    }

    /**
     * Typ podmiotu, którego dotyczą kryteria filtrowania metadanych faktur. Określa kontekst, w jakim przeszukiwane są dane.
     */
    public InvoiceQuerySubjectType getSubjectType() {
        return subjectType;
    }

    /**
     * Typ podmiotu, którego dotyczą kryteria filtrowania metadanych faktur. Określa kontekst, w jakim przeszukiwane są dane.
     */
    public void setSubjectType(InvoiceQuerySubjectType subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * Typ i zakres dat, według którego filtrowane są faktury. Maksymalny dozwolony okres wynosi 100 dni w strefie UTC
     * <p>
     * Format daty: * Daty muszą być przekazane w formacie ISO 8601, np. {@code yyyy-MM-ddTHH:mm:ss}. * Dopuszczalne są następujące warianty: - z sufiksem {@code Z} (czas UTC), - z jawnym offsetem, np. {@code +01:00}, {@code +03:00}
     */
    public InvoiceQueryDateRange getDateRange() {
        return dateRange;
    }

    /**
     * Typ i zakres dat, według którego filtrowane są faktury. Maksymalny dozwolony okres wynosi 100 dni w strefie UTC
     * <p>
     * Format daty: * Daty muszą być przekazane w formacie ISO 8601, np. {@code yyyy-MM-ddTHH:mm:ss}. * Dopuszczalne są następujące warianty: - z sufiksem {@code Z} (czas UTC), - z jawnym offsetem, np. {@code +01:00}, {@code +03:00}
     */
    public void setDateRange(InvoiceQueryDateRange dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * Numer KSeF faktury (exact match).
     */
    public String getKsefNumber() {
        return ksefNumber;
    }

    /**
     * Numer KSeF faktury (exact match).
     */
    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    /**
     * Numer faktury nadany przez wystawcę (exact match).
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Numer faktury nadany przez wystawcę (exact match).
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Filtr kwotowy – brutto, netto lub VAT (z wartością).
     */
    public InvoiceQueryAmount getAmount() {
        return amount;
    }

    /**
     * Filtr kwotowy – brutto, netto lub VAT (z wartością).
     */
    public void setAmount(InvoiceQueryAmount amount) {
        this.amount = amount;
    }

    /**
     * Nip sprzedawcy (exact match).
     */
    public String getSellerNip() {
        return sellerNip;
    }

    /**
     * Nip sprzedawcy (exact match).
     */
    public void setSellerNip(String sellerNip) {
        this.sellerNip = sellerNip;
    }

    /**
     * Identyfikator nabywcy.
     */
    public InvoiceBuyerIdentifier getBuyerIdentifier() {
        return buyerIdentifier;
    }

    /**
     * Identyfikator nabywcy.
     */
    public void setBuyerIdentifier(InvoiceBuyerIdentifier buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    /**
     * Kody walut.
     */
    public List<CurrencyCode> getCurrencyCodes() {
        return currencyCodes;
    }

    /**
     * Kody walut.
     */
    public void setCurrencyCodes(List<CurrencyCode> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    /**
     * Tryb wystawienia faktury: online lub offline.
     */
    public InvoicingMode getInvoicingMode() {
        return invoicingMode;
    }

    /**
     * Tryb wystawienia faktury: online lub offline.
     */
    public void setInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
    }

    /**
     * Czy faktura została wystawiona w trybie samofakturowania.
     */
    public Boolean getIsSelfInvoicing() {
        return isSelfInvoicing;
    }

    /**
     * Czy faktura została wystawiona w trybie samofakturowania.
     */
    public void setIsSelfInvoicing(Boolean isSelfInvoicing) {
        this.isSelfInvoicing = isSelfInvoicing;
    }

    /**
     * Typ dokumentu.
     */
    public InvoiceFormType getFormType() {
        return formType;
    }

    /**
     * Typ dokumentu.
     */
    public void setFormType(InvoiceFormType formType) {
        this.formType = formType;
    }

    /**
     * Rodzaje faktur.
     */
    public List<InvoiceMetadataInvoiceType> getInvoiceTypes() {
        return invoiceTypes;
    }

    /**
     * Rodzaje faktur.
     */
    public void setInvoiceTypes(List<InvoiceMetadataInvoiceType> invoiceTypes) {
        this.invoiceTypes = invoiceTypes;
    }

    /**
     * Czy faktura ma załącznik.
     */
    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    /**
     * Czy faktura ma załącznik.
     */
    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}

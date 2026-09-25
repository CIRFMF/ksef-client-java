package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * InvoiceMetadata.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceMetadata {

    /**
     * Numer KSeF faktury.
     */
    private String ksefNumber;

    /**
     * Numer faktury nadany przez wystawcę.
     */
    private String invoiceNumber;

    /**
     * Data przyjęcia faktury w systemie KSeF (do dalszego przetwarzania).
     */
    private OffsetDateTime invoicingDate;

    /**
     * Data wystawienia faktury.
     */
    private LocalDate issueDate;

    /**
     * Data nadania numeru KSeF.
     */
    private OffsetDateTime acquisitionDate;

    /**
     * Data trwałego zapisu faktury w repozytorium systemu KSeF.
     */
    private OffsetDateTime permanentStorageDate;

    /**
     * Dane identyfikujące sprzedawcę.
     */
    private InvoiceMetadataSeller seller;

    /**
     * Dane identyfikujące nabywcę.
     */
    private InvoiceMetadataBuyer buyer;

    /**
     * Łączna kwota netto.
     */
    private Double netAmount;

    /**
     * Łączna kwota brutto.
     */
    private Double grossAmount;

    /**
     * Łączna kwota VAT wyrażona w PLN.
     */
    private Double vatAmount;

    /**
     * Kod waluty.
     */
    private String currency;

    /**
     * Tryb fakturowania (online/offline).
     */
    private InvoicingMode invoicingMode;

    /**
     * Rodzaj faktury.
     */
    private InvoiceMetadataInvoiceType invoiceType;

    /**
     * Struktura dokumentu faktury.
     * <p>
     * Obsługiwane schematy:
     */
    private InvoiceFormCode formCode;

    /**
     * Czy faktura została wystawiona w trybie samofakturowania.
     */
    private Boolean isSelfInvoicing;

    /**
     * Określa, czy faktura posiada załącznik.
     */
    private Boolean hasAttachment;

    /**
     * Skrót SHA256 faktury, zakodowany w formacie Base64.
     */
    private String invoiceHash;

    /**
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64.
     */
    private String hashOfCorrectedInvoice;

    /**
     * Podmiot upoważniony.
     */
    private AuthorizedSubject authorizedSubject;

    /**
     * Lista podmiotów trzecich.
     */
    private List<ThirdSubject> thirdSubjects;

    public InvoiceMetadata() {
    }

    /**
     * Numer KSeF faktury.
     */
    public String getKsefNumber() {
        return ksefNumber;
    }

    /**
     * Numer KSeF faktury.
     */
    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    /**
     * Numer faktury nadany przez wystawcę.
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Numer faktury nadany przez wystawcę.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Data wystawienia faktury.
     */
    public LocalDate getIssueDate() {
        return issueDate;
    }

    /**
     * Data wystawienia faktury.
     */
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
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
     * Data trwałego zapisu faktury w repozytorium systemu KSeF.
     */
    public OffsetDateTime getPermanentStorageDate() {
        return permanentStorageDate;
    }

    /**
     * Data trwałego zapisu faktury w repozytorium systemu KSeF.
     */
    public void setPermanentStorageDate(OffsetDateTime permanentStorageDate) {
        this.permanentStorageDate = permanentStorageDate;
    }

    /**
     * Dane identyfikujące sprzedawcę.
     */
    public InvoiceMetadataSeller getSeller() {
        return seller;
    }

    /**
     * Dane identyfikujące sprzedawcę.
     */
    public void setSeller(InvoiceMetadataSeller seller) {
        this.seller = seller;
    }

    /**
     * Dane identyfikujące nabywcę.
     */
    public InvoiceMetadataBuyer getBuyer() {
        return buyer;
    }

    /**
     * Dane identyfikujące nabywcę.
     */
    public void setBuyer(InvoiceMetadataBuyer buyer) {
        this.buyer = buyer;
    }

    /**
     * Łączna kwota netto.
     */
    public Double getNetAmount() {
        return netAmount;
    }

    /**
     * Łączna kwota netto.
     */
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Łączna kwota brutto.
     */
    public Double getGrossAmount() {
        return grossAmount;
    }

    /**
     * Łączna kwota brutto.
     */
    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    /**
     * Łączna kwota VAT wyrażona w PLN.
     */
    public Double getVatAmount() {
        return vatAmount;
    }

    /**
     * Łączna kwota VAT wyrażona w PLN.
     */
    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    /**
     * Kod waluty.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Kod waluty.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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

    /**
     * Rodzaj faktury.
     */
    public InvoiceMetadataInvoiceType getInvoiceType() {
        return invoiceType;
    }

    /**
     * Rodzaj faktury.
     */
    public void setInvoiceType(InvoiceMetadataInvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * Struktura dokumentu faktury.
     * <p>
     * Obsługiwane schematy:
     */
    public InvoiceFormCode getFormCode() {
        return formCode;
    }

    /**
     * Struktura dokumentu faktury.
     * <p>
     * Obsługiwane schematy:
     */
    public void setFormCode(InvoiceFormCode formCode) {
        this.formCode = formCode;
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
    public void setIsSelfInvoicing(Boolean selfInvoicing) {
        isSelfInvoicing = selfInvoicing;
    }

    /**
     * Określa, czy faktura posiada załącznik.
     */
    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    /**
     * Określa, czy faktura posiada załącznik.
     */
    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
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
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64.
     */
    public String getHashOfCorrectedInvoice() {
        return hashOfCorrectedInvoice;
    }

    /**
     * Skrót SHA256 korygowanej faktury, zakodowany w formacie Base64.
     */
    public void setHashOfCorrectedInvoice(String hashOfCorrectedInvoice) {
        this.hashOfCorrectedInvoice = hashOfCorrectedInvoice;
    }

    /**
     * Podmiot upoważniony.
     */
    public AuthorizedSubject getAuthorizedSubject() {
        return authorizedSubject;
    }

    /**
     * Podmiot upoważniony.
     */
    public void setAuthorizedSubject(AuthorizedSubject authorizedSubject) {
        this.authorizedSubject = authorizedSubject;
    }

    /**
     * Lista podmiotów trzecich.
     */
    public List<ThirdSubject> getThirdSubjects() {
        return thirdSubjects;
    }

    /**
     * Lista podmiotów trzecich.
     */
    public void setThirdSubjects(List<ThirdSubject> thirdSubjects) {
        this.thirdSubjects = thirdSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceMetadata that = (InvoiceMetadata) o;
        return this.ksefNumber.equals(that.ksefNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ksefNumber);
    }
}

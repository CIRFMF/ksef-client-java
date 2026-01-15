package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;
import java.util.List;

public class InvoiceExportPackage {
    // Liczba faktur w paczce.
    private int invoiceCount;
    // Rozmiar paczki w bajtach.
    private int size;
    // Lista części paczki do pobrania.
    private List<InvoicePackagePart> parts;
    // Czy paczka została obcięta (nie zawiera wszystkich faktur z zakresu).
    private Boolean isTruncated;
    // Data wystawienia ostatniej faktury w paczce.
    private OffsetDateTime lastIssueDate;
    // Data sprzedaży ostatniej faktury w paczce.
    private OffsetDateTime lastInvoicingDate;
    // Data trwałego przechowywania ostatniej faktury w paczce.
    private OffsetDateTime lastPermanentStorageDate;
    // Dotyczy wyłącznie zapytań filtrowanych po typie daty PermanentStorage. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania.
    private OffsetDateTime permanentStorageHwmDate;

    public InvoiceExportPackage() {

    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<InvoicePackagePart> getParts() {
        return parts;
    }

    public void setParts(List<InvoicePackagePart> parts) {
        this.parts = parts;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public OffsetDateTime getLastIssueDate() {
        return lastIssueDate;
    }

    public void setLastIssueDate(OffsetDateTime lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    public OffsetDateTime getLastInvoicingDate() {
        return lastInvoicingDate;
    }

    public void setLastInvoicingDate(OffsetDateTime lastInvoicingDate) {
        this.lastInvoicingDate = lastInvoicingDate;
    }

    public OffsetDateTime getLastPermanentStorageDate() {
        return lastPermanentStorageDate;
    }

    public void setLastPermanentStorageDate(OffsetDateTime lastPermanentStorageDate) {
        this.lastPermanentStorageDate = lastPermanentStorageDate;
    }

    public OffsetDateTime getPermanentStorageHwmDate() {
        return permanentStorageHwmDate;
    }

    public void setPermanentStorageHwmDate(OffsetDateTime permanentStorageHwmDate) {
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }
}

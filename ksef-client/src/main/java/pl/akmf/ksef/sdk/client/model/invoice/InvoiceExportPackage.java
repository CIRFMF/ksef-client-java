package pl.akmf.ksef.sdk.client.model.invoice;

import pl.akmf.ksef.sdk.client.model.session.batch.CompressionType;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * InvoiceExportPackage.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InvoicePackage}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceExportPackage {

    /**
     * Łączna liczba faktur w paczce.
     */
    private int invoiceCount;

    /**
     * Rozmiar paczki w bajtach.
     */
    private int size;

    /**
     * Lista dostępnych części paczki do pobrania.
     */
    private List<InvoicePackagePart> parts;

    /**
     * Określa, czy wynik eksportu został ucięty z powodu przekroczenia limitu liczby faktur lub wielkości paczki.
     */
    private Boolean isTruncated;

    /**
     * Data wystawienia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Issue}.
     */
    private OffsetDateTime lastIssueDate;

    // Data sprzedaży ostatniej faktury w paczce.
    /**
     * Data przyjęcia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Invoicing}.
     */
    private OffsetDateTime lastInvoicingDate;

    /**
     * Data trwałego zapisu ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code PermanentStorage}.
     */
    private OffsetDateTime lastPermanentStorageDate;

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     */
    private OffsetDateTime permanentStorageHwmDate;

    // Typ kompresji użyty do przygotowania paczki eksportu.
    private CompressionType compressionType;

    public InvoiceExportPackage() {
    }

    /**
     * Łączna liczba faktur w paczce.
     */
    public int getInvoiceCount() {
        return invoiceCount;
    }

    /**
     * Łączna liczba faktur w paczce.
     */
    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    /**
     * Rozmiar paczki w bajtach.
     */
    public int getSize() {
        return size;
    }

    /**
     * Rozmiar paczki w bajtach.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Lista dostępnych części paczki do pobrania.
     */
    public List<InvoicePackagePart> getParts() {
        return parts;
    }

    /**
     * Lista dostępnych części paczki do pobrania.
     */
    public void setParts(List<InvoicePackagePart> parts) {
        this.parts = parts;
    }

    /**
     * Określa, czy wynik eksportu został ucięty z powodu przekroczenia limitu liczby faktur lub wielkości paczki.
     */
    public Boolean getIsTruncated() {
        return isTruncated;
    }

    /**
     * Określa, czy wynik eksportu został ucięty z powodu przekroczenia limitu liczby faktur lub wielkości paczki.
     */
    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    /**
     * Data wystawienia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Issue}.
     */
    public OffsetDateTime getLastIssueDate() {
        return lastIssueDate;
    }

    /**
     * Data wystawienia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Issue}.
     */
    public void setLastIssueDate(OffsetDateTime lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    /**
     * Data przyjęcia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Invoicing}.
     */
    public OffsetDateTime getLastInvoicingDate() {
        return lastInvoicingDate;
    }

    /**
     * Data przyjęcia ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code Invoicing}.
     */
    public void setLastInvoicingDate(OffsetDateTime lastInvoicingDate) {
        this.lastInvoicingDate = lastInvoicingDate;
    }

    /**
     * Data trwałego zapisu ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code PermanentStorage}.
     */
    public OffsetDateTime getLastPermanentStorageDate() {
        return lastPermanentStorageDate;
    }

    /**
     * Data trwałego zapisu ostatniej faktury ujętej w paczce. Pole występuje wyłącznie wtedy, gdy paczka została ucięta i eksport był filtrowany po typie daty {@code PermanentStorage}.
     */
    public void setLastPermanentStorageDate(OffsetDateTime lastPermanentStorageDate) {
        this.lastPermanentStorageDate = lastPermanentStorageDate;
    }

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     */
    public OffsetDateTime getPermanentStorageHwmDate() {
        return permanentStorageHwmDate;
    }

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     */
    public void setPermanentStorageHwmDate(OffsetDateTime permanentStorageHwmDate) {
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }

    public CompressionType getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(CompressionType compressionType) {
        this.compressionType = compressionType;
    }
}

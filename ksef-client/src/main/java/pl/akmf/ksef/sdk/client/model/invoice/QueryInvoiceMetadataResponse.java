package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * QueryInvoiceMetadataResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code QueryInvoicesMetadataResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryInvoiceMetadataResponse {

    /**
     * Określa, czy istnieją kolejne wyniki zapytania.
     */
    private Boolean hasMore;

    /**
     * Określa, czy osiągnięto maksymalny dopuszczalny zakres wyników zapytania (10 000).
     */
    private Boolean isTruncated;

    /**
     * Lista faktur spełniających kryteria.
     */
    private List<InvoiceMetadata> invoices = new ArrayList<>();

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * Wartość jest stała dla wszystkich stron tego samego zapytania i nie zależy od paginacji ani sortowania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     *
     * Górna granica daty PermanentStorage (UTC), do której system uwzględnił dane w ramach tego zapytania - HWM (high water mark).
     */
    private OffsetDateTime permanentStorageHwmDate;

    public QueryInvoiceMetadataResponse() {
    }

    public QueryInvoiceMetadataResponse(Boolean hasMore, List<InvoiceMetadata> invoices, Boolean isTruncated) {
        this.hasMore = hasMore;
        this.invoices = invoices;
        this.isTruncated = isTruncated;
    }

    public QueryInvoiceMetadataResponse(Boolean hasMore, Boolean isTruncated, List<InvoiceMetadata> invoices, OffsetDateTime permanentStorageHwmDate) {
        this.hasMore = hasMore;
        this.isTruncated = isTruncated;
        this.invoices = invoices;
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }

    /**
     * Lista faktur spełniających kryteria.
     */
    public List<InvoiceMetadata> getInvoices() {
        return invoices;
    }

    /**
     * Lista faktur spełniających kryteria.
     */
    public void setInvoices(List<InvoiceMetadata> invoices) {
        this.invoices = invoices;
    }

    /**
     * Określa, czy istnieją kolejne wyniki zapytania.
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     * Określa, czy istnieją kolejne wyniki zapytania.
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * Określa, czy osiągnięto maksymalny dopuszczalny zakres wyników zapytania (10 000).
     */
    public Boolean getIsTruncated() {
        return isTruncated;
    }

    /**
     * Określa, czy osiągnięto maksymalny dopuszczalny zakres wyników zapytania (10 000).
     */
    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * Wartość jest stała dla wszystkich stron tego samego zapytania i nie zależy od paginacji ani sortowania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     *
     * Górna granica daty PermanentStorage (UTC), do której system uwzględnił dane w ramach tego zapytania - HWM (high water mark).
     */
    public OffsetDateTime getPermanentStorageHwmDate() {
        return permanentStorageHwmDate;
    }

    /**
     * Dotyczy wyłącznie zapytań filtrowanych po typie daty &lt;b&gt;PermanentStorage&lt;/b&gt;. Jeśli zapytanie dotyczyło najnowszego okresu, wartość ta może być wartością nieznacznie skorygowaną względem górnej granicy podanej w warunkach zapytania. Dla okresów starszych, będzie to zgodne z warunkami zapytania.
     * <p>
     * Wartość jest stała dla wszystkich stron tego samego zapytania i nie zależy od paginacji ani sortowania.
     * <p>
     * System gwarantuje, że dane poniżej tej wartości są spójne i kompletne. Ponowne zapytania obejmujące zakresem dane poniżej tego kroczącego znacznika czasu nie zwrócą w przyszłości innych wyników (np.dodatkowych faktur).
     * <p>
     * Dla dateType = Issue lub Invoicing – null.
     *
     * Górna granica daty PermanentStorage (UTC), do której system uwzględnił dane w ramach tego zapytania - HWM (high water mark).
     */
    public void setPermanentStorageHwmDate(OffsetDateTime permanentStorageHwmDate) {
        this.permanentStorageHwmDate = permanentStorageHwmDate;
    }
}

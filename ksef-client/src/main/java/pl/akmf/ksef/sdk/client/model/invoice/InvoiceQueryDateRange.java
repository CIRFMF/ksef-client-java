package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;

/**
 * InvoiceQueryDateRange.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceQueryDateRange {

    /**
     * Typ daty, według której ma być zastosowany zakres.
     */
    private InvoiceQueryDateType dateType;

    /**
     * Data początkowa zakresu w formacie ISO-8601 np. 2026-01-03T13:45:00+00:00.
     */
    private OffsetDateTime from;

    /**
     * Data końcowa zakresu w formacie ISO-8601. Jeśli nie zostanie podana, przyjmowana jest bieżąca data i czas w UTC.
     */
    private OffsetDateTime to;

    /**
     * Określa, czy system ma ograniczyć filtrowanie (zakres dateRange.to) do wartości {@code PermanentStorageHwmDate}.
     * <p>
     * * Dotyczy wyłącznie zapytań z {@code dateType = PermanentStorage}, * Gdy {@code true}, system ogranicza filtrowanie tak, aby wartość {@code dateRange.to} nie przekraczała wartości {@code PermanentStorageHwmDate}, * Gdy {@code null} lub {@code false}, filtrowanie może wykraczać poza {@code PermanentStorageHwmDate}.
     */
    private Boolean restrictToPermanentStorageHwmDate;

    public InvoiceQueryDateRange() {
    }

    public InvoiceQueryDateRange(InvoiceQueryDateType dateType, OffsetDateTime from, OffsetDateTime to) {
        this.dateType = dateType;
        this.from = from;
        this.to = to;
    }

    public InvoiceQueryDateRange(InvoiceQueryDateType dateType, OffsetDateTime from, OffsetDateTime to, Boolean restrictToPermanentStorageHwmDate) {
        this.dateType = dateType;
        this.from = from;
        this.to = to;
        this.restrictToPermanentStorageHwmDate = restrictToPermanentStorageHwmDate;
    }

    /**
     * Typ daty, według której ma być zastosowany zakres.
     */
    public InvoiceQueryDateType getDateType() {
        return dateType;
    }

    /**
     * Typ daty, według której ma być zastosowany zakres.
     */
    public void setDateType(InvoiceQueryDateType dateType) {
        this.dateType = dateType;
    }

    /**
     * Data początkowa zakresu w formacie ISO-8601 np. 2026-01-03T13:45:00+00:00.
     */
    public OffsetDateTime getFrom() {
        return from;
    }

    /**
     * Data początkowa zakresu w formacie ISO-8601 np. 2026-01-03T13:45:00+00:00.
     */
    public void setFrom(OffsetDateTime from) {
        this.from = from;
    }

    /**
     * Data końcowa zakresu w formacie ISO-8601. Jeśli nie zostanie podana, przyjmowana jest bieżąca data i czas w UTC.
     */
    public OffsetDateTime getTo() {
        return to;
    }

    /**
     * Data końcowa zakresu w formacie ISO-8601. Jeśli nie zostanie podana, przyjmowana jest bieżąca data i czas w UTC.
     */
    public void setTo(OffsetDateTime to) {
        this.to = to;
    }

    /**
     * Określa, czy system ma ograniczyć filtrowanie (zakres dateRange.to) do wartości {@code PermanentStorageHwmDate}.
     * <p>
     *  Dotyczy wyłącznie zapytań z {@code dateType = PermanentStorage}, * Gdy {@code true}, system ogranicza filtrowanie tak, aby wartość {@code dateRange.to} nie przekraczała wartości {@code PermanentStorageHwmDate}, * Gdy {@code null} lub {@code false}, filtrowanie może wykraczać poza {@code PermanentStorageHwmDate}.
     */
    public Boolean getRestrictToPermanentStorageHwmDate() {
        return restrictToPermanentStorageHwmDate;
    }

    /**
     * Określa, czy system ma ograniczyć filtrowanie (zakres dateRange.to) do wartości {@code PermanentStorageHwmDate}.
     * <p>
     *  Dotyczy wyłącznie zapytań z {@code dateType = PermanentStorage}, * Gdy {@code true}, system ogranicza filtrowanie tak, aby wartość {@code dateRange.to} nie przekraczała wartości {@code PermanentStorageHwmDate}, * Gdy {@code null} lub {@code false}, filtrowanie może wykraczać poza {@code PermanentStorageHwmDate}.
     */
    public void setRestrictToPermanentStorageHwmDate(Boolean restrictToPermanentStorageHwmDate) {
        this.restrictToPermanentStorageHwmDate = restrictToPermanentStorageHwmDate;
    }
}

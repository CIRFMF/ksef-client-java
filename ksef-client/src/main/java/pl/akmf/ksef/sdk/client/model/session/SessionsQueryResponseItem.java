package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import java.time.OffsetDateTime;

/**
 * SessionsQueryResponseItem
 */
public class SessionsQueryResponseItem {

    /**
     * Numer referencyjny sesji.
     */
    private String referenceNumber;

    /**
     * Status sesji.
     */
    private StatusInfo status;

    /**
     * Data utworzenia sesji.
     */
    private OffsetDateTime dateCreated;

    /**
     * Data ostatniej aktywności w ramach sesji.
     */
    private OffsetDateTime dateUpdated;

    /**
     * Termin ważności sesji. Po jego upływie sesja interaktywna zostanie automatycznie zamknięta.
     */
    private OffsetDateTime validUntil;

    /**
     * Łączna liczba faktur (uwzględnia również te w trakcie przetwarzania).
     */
    private Integer totalInvoiceCount;

    /**
     * Liczba poprawnie przetworzonych faktur.
     */
    private Integer successfulInvoiceCount;

    /**
     * Liczba błędnie przetworzonych faktur.
     */
    private Integer failedInvoiceCount;

    public SessionsQueryResponseItem() {
    }

    /**
     * Numer referencyjny sesji.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny sesji.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Status sesji.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Status sesji.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Data utworzenia sesji.
     */
    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Data utworzenia sesji.
     */
    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Data ostatniej aktywności w ramach sesji.
     */
    public OffsetDateTime getDateUpdated() {
        return dateUpdated;
    }

    /**
     * Data ostatniej aktywności w ramach sesji.
     */
    public void setDateUpdated(OffsetDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja interaktywna zostanie automatycznie zamknięta.
     */
    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja interaktywna zostanie automatycznie zamknięta.
     */
    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Łączna liczba faktur (uwzględnia również te w trakcie przetwarzania).
     */
    public Integer getTotalInvoiceCount() {
        return totalInvoiceCount;
    }

    /**
     * Łączna liczba faktur (uwzględnia również te w trakcie przetwarzania).
     */
    public void setTotalInvoiceCount(Integer totalInvoiceCount) {
        this.totalInvoiceCount = totalInvoiceCount;
    }

    /**
     * Liczba poprawnie przetworzonych faktur.
     */
    public Integer getSuccessfulInvoiceCount() {
        return successfulInvoiceCount;
    }

    /**
     * Liczba poprawnie przetworzonych faktur.
     */
    public void setSuccessfulInvoiceCount(Integer successfulInvoiceCount) {
        this.successfulInvoiceCount = successfulInvoiceCount;
    }

    /**
     * Liczba błędnie przetworzonych faktur.
     */
    public Integer getFailedInvoiceCount() {
        return failedInvoiceCount;
    }

    /**
     * Liczba błędnie przetworzonych faktur.
     */
    public void setFailedInvoiceCount(Integer failedInvoiceCount) {
        this.failedInvoiceCount = failedInvoiceCount;
    }
}

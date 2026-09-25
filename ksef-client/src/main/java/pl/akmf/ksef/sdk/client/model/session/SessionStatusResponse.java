package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import java.time.OffsetDateTime;

/**
 * SessionStatusResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SessionStatusResponse {

    /**
     * Informacje o aktualnym statusie.
     * <p>
     * Sesja wsadowa: Sesja interaktywna:
     */
    private StatusInfo status;

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    private OffsetDateTime validUntil;

    /**
     * Data utworzenia sesji.
     */
    private OffsetDateTime dateCreated;

    /**
     * Data ostatniej aktywności w ramach sesji.
     */
    private OffsetDateTime dateUpdated;

    /**
     * Informacja o UPO sesyjnym, zwracana gdy sesja została zamknięta i UPO zostało wygenerowane.
     */
    private UpoResponse upo;

    /**
     * Liczba przyjętych faktur w ramach sesji.
     */
    private Integer invoiceCount;

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z sukcesem .
     */
    private Integer successfulInvoiceCount;

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z błędem.
     */
    private Integer failedInvoiceCount;

    public SessionStatusResponse() {
    }

    public SessionStatusResponse(StatusInfo status, UpoResponse upo, Integer invoiceCount, Integer successfulInvoiceCount, Integer failedInvoiceCount) {
        this.status = status;
        this.upo = upo;
        this.invoiceCount = invoiceCount;
        this.successfulInvoiceCount = successfulInvoiceCount;
        this.failedInvoiceCount = failedInvoiceCount;
    }

    public SessionStatusResponse(StatusInfo status, OffsetDateTime validUntil, OffsetDateTime dateCreated, OffsetDateTime dateUpdated, UpoResponse upo, Integer invoiceCount, Integer successfulInvoiceCount, Integer failedInvoiceCount) {
        this.status = status;
        this.validUntil = validUntil;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.upo = upo;
        this.invoiceCount = invoiceCount;
        this.successfulInvoiceCount = successfulInvoiceCount;
        this.failedInvoiceCount = failedInvoiceCount;
    }

    /**
     * Informacje o aktualnym statusie.
     * <p>
     * Sesja wsadowa: Sesja interaktywna:
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Informacje o aktualnym statusie.
     * <p>
     * Sesja wsadowa: Sesja interaktywna:
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Termin ważności sesji. Po jego upływie sesja zostanie automatycznie zamknięta.
     */
    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
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
     * Informacja o UPO sesyjnym, zwracana gdy sesja została zamknięta i UPO zostało wygenerowane.
     */
    public UpoResponse getUpo() {
        return upo;
    }

    /**
     * Informacja o UPO sesyjnym, zwracana gdy sesja została zamknięta i UPO zostało wygenerowane.
     */
    public void setUpo(UpoResponse upo) {
        this.upo = upo;
    }

    /**
     * Liczba przyjętych faktur w ramach sesji.
     */
    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    /**
     * Liczba przyjętych faktur w ramach sesji.
     */
    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z sukcesem .
     */
    public Integer getSuccessfulInvoiceCount() {
        return successfulInvoiceCount;
    }

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z sukcesem .
     */
    public void setSuccessfulInvoiceCount(Integer successfulInvoiceCount) {
        this.successfulInvoiceCount = successfulInvoiceCount;
    }

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z błędem.
     */
    public Integer getFailedInvoiceCount() {
        return failedInvoiceCount;
    }

    /**
     * Liczba faktur przeprocesowanych w ramach sesji z błędem.
     */
    public void setFailedInvoiceCount(Integer failedInvoiceCount) {
        this.failedInvoiceCount = failedInvoiceCount;
    }
}

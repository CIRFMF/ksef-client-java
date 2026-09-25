package pl.akmf.ksef.sdk.client.model.limit;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ApiRateLimitsOverride.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ApiRateLimitsChangeRequest {

    /**
     * Limity dla otwierania sesji interaktywnych.
     */
    private OnlineSessionRateLimit onlineSession;

    /**
     * Limity dla otwierania sesji wsadowych.
     */
    private BatchSessionRateLimit batchSession;

    /**
     * Limity wysyłki faktur.
     */
    private InvoiceSendRateLimit invoiceSend;

    /**
     * Limity dla pobierania statusu faktury z sesji.
     */
    private InvoiceStatusRateLimit invoiceStatus;

    /**
     * Limity pobierania listy sesji.
     */
    private SessionListRateLimit sessionList;

    /**
     * Limity pobierania listy faktur w sesji.
     */
    private SessionInvoiceListRateLimit sessionInvoiceList;

    /**
     * Limity pozostałych operacji w ramach sesji.
     */
    private SessionMiscRateLimits sessionMisc;

    /**
     * Limity pobierania metadanych faktur.
     */
    private InvoiceMetadataRateLimit invoiceMetadata;

    /**
     * Limity eksportu paczki faktur.
     */
    private InvoiceExportRateLimit invoiceExport;

    /**
     * Limity dla pobierania statusu eksportu paczki faktur.
     */
    @JsonProperty("invoiceExportStatus")
    private InvoiceExportStatusRateLimit invoiceStatusExport;

    /**
     * Limity pobierania faktur po numerze KSeF.
     */
    private InvoiceDownloadRateLimit invoiceDownload;

    /**
     * Limity pozostałych operacji API.
     */
    private OtherRateLimit other;

    /**
     * Limity generowania identyfikatorów zbiorczych.
     */
    private CollectiveIdentifierRateLimit collectiveIdentifier;

    public ApiRateLimitsChangeRequest() {
    }

    public ApiRateLimitsChangeRequest(OnlineSessionRateLimit onlineSession, BatchSessionRateLimit batchSession, InvoiceSendRateLimit invoiceSend, InvoiceStatusRateLimit invoiceStatus, SessionListRateLimit sessionList, SessionInvoiceListRateLimit sessionInvoiceList, SessionMiscRateLimits sessionMisc, InvoiceMetadataRateLimit invoiceMetadata, InvoiceExportRateLimit invoiceExport, InvoiceExportStatusRateLimit invoiceStatusExport, InvoiceDownloadRateLimit invoiceDownload, OtherRateLimit other, CollectiveIdentifierRateLimit collectiveIdentifier) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
        this.invoiceSend = invoiceSend;
        this.invoiceStatus = invoiceStatus;
        this.sessionList = sessionList;
        this.sessionInvoiceList = sessionInvoiceList;
        this.sessionMisc = sessionMisc;
        this.invoiceMetadata = invoiceMetadata;
        this.invoiceExport = invoiceExport;
        this.invoiceStatusExport = invoiceStatusExport;
        this.invoiceDownload = invoiceDownload;
        this.other = other;
        this.collectiveIdentifier = collectiveIdentifier;
    }

    /**
     * Limity dla otwierania sesji interaktywnych.
     */
    public OnlineSessionRateLimit getOnlineSession() {
        return onlineSession;
    }

    /**
     * Limity dla otwierania sesji interaktywnych.
     */
    public void setOnlineSession(OnlineSessionRateLimit onlineSession) {
        this.onlineSession = onlineSession;
    }

    /**
     * Limity dla otwierania sesji wsadowych.
     */
    public BatchSessionRateLimit getBatchSession() {
        return batchSession;
    }

    /**
     * Limity dla otwierania sesji wsadowych.
     */
    public void setBatchSession(BatchSessionRateLimit batchSession) {
        this.batchSession = batchSession;
    }

    /**
     * Limity wysyłki faktur.
     */
    public InvoiceSendRateLimit getInvoiceSend() {
        return invoiceSend;
    }

    /**
     * Limity wysyłki faktur.
     */
    public void setInvoiceSend(InvoiceSendRateLimit invoiceSend) {
        this.invoiceSend = invoiceSend;
    }

    /**
     * Limity dla pobierania statusu faktury z sesji.
     */
    public InvoiceStatusRateLimit getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * Limity dla pobierania statusu faktury z sesji.
     */
    public void setInvoiceStatus(InvoiceStatusRateLimit invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * Limity pobierania listy sesji.
     */
    public SessionListRateLimit getSessionList() {
        return sessionList;
    }

    /**
     * Limity pobierania listy sesji.
     */
    public void setSessionList(SessionListRateLimit sessionList) {
        this.sessionList = sessionList;
    }

    /**
     * Limity pobierania listy faktur w sesji.
     */
    public SessionInvoiceListRateLimit getSessionInvoiceList() {
        return sessionInvoiceList;
    }

    /**
     * Limity pobierania listy faktur w sesji.
     */
    public void setSessionInvoiceList(SessionInvoiceListRateLimit sessionInvoiceList) {
        this.sessionInvoiceList = sessionInvoiceList;
    }

    /**
     * Limity pozostałych operacji w ramach sesji.
     */
    public SessionMiscRateLimits getSessionMisc() {
        return sessionMisc;
    }

    /**
     * Limity pozostałych operacji w ramach sesji.
     */
    public void setSessionMisc(SessionMiscRateLimits sessionMisc) {
        this.sessionMisc = sessionMisc;
    }

    /**
     * Limity pobierania metadanych faktur.
     */
    public InvoiceMetadataRateLimit getInvoiceMetadata() {
        return invoiceMetadata;
    }

    /**
     * Limity pobierania metadanych faktur.
     */
    public void setInvoiceMetadata(InvoiceMetadataRateLimit invoiceMetadata) {
        this.invoiceMetadata = invoiceMetadata;
    }

    /**
     * Limity eksportu paczki faktur.
     */
    public InvoiceExportRateLimit getInvoiceExport() {
        return invoiceExport;
    }

    /**
     * Limity eksportu paczki faktur.
     */
    public void setInvoiceExport(InvoiceExportRateLimit invoiceExport) {
        this.invoiceExport = invoiceExport;
    }

    /**
     * Limity dla pobierania statusu eksportu paczki faktur.
     */
    public InvoiceExportStatusRateLimit getInvoiceStatusExport() {
        return invoiceStatusExport;
    }

    /**
     * Limity dla pobierania statusu eksportu paczki faktur.
     */
    public void setInvoiceStatusExport(InvoiceExportStatusRateLimit invoiceStatusExport) {
        this.invoiceStatusExport = invoiceStatusExport;
    }

    /**
     * Limity pobierania faktur po numerze KSeF.
     */
    public InvoiceDownloadRateLimit getInvoiceDownload() {
        return invoiceDownload;
    }

    /**
     * Limity pobierania faktur po numerze KSeF.
     */
    public void setInvoiceDownload(InvoiceDownloadRateLimit invoiceDownload) {
        this.invoiceDownload = invoiceDownload;
    }

    /**
     * Limity pozostałych operacji API.
     */
    public OtherRateLimit getOther() {
        return other;
    }

    /**
     * Limity pozostałych operacji API.
     */
    public void setOther(OtherRateLimit other) {
        this.other = other;
    }

    /**
     * Limity generowania identyfikatorów zbiorczych.
     */
    public CollectiveIdentifierRateLimit getCollectiveIdentifier() {
        return collectiveIdentifier;
    }

    /**
     * Limity generowania identyfikatorów zbiorczych.
     */
    public void setCollectiveIdentifier(CollectiveIdentifierRateLimit collectiveIdentifier) {
        this.collectiveIdentifier = collectiveIdentifier;
    }
}

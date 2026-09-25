package pl.akmf.ksef.sdk.client.model.limit;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetRateLimitResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code EffectiveApiRateLimits}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GetRateLimitResponse {

    /**
     * Limity dla otwierania sesji interaktywnych.
     */
    private OnlineSessionRateLimit onlineSession;

    /**
     * Limity dla zamykania sesji interaktywnych.
     */
    private OnlineSessionRateLimit onlineSessionClose;

    /**
     * Limity dla otwierania sesji wsadowych.
     */
    private BatchSessionRateLimit batchSession;

    /**
     * Limity dla zamykania sesji wsadowych.
     */
    private BatchSessionRateLimit batchSessionClose;

    /**
     * Limity dla wysyłki faktur.
     */
    private InvoiceSendRateLimit invoiceSend;

    /**
     * Limity dla pobierania statusu faktury z sesji.
     */
    private InvoiceStatusRateLimit invoiceStatus;

    /**
     * Limity dla pobierania listy sesji.
     */
    private SessionListRateLimit sessionList;

    /**
     * Limity dla pobierania listy faktur w sesji.
     */
    private SessionInvoiceListRateLimit sessionInvoiceList;

    /**
     * Limity dla pozostałych operacji w ramach sesji.
     */
    private SessionMiscRateLimits sessionMisc;

    /**
     * Limity dla pobierania metadanych faktur.
     */
    private InvoiceMetadataRateLimit invoiceMetadata;

    /**
     * Limity dla eksportu paczki faktur.
     */
    private InvoiceExportRateLimit invoiceExport;

    /**
     * Limity dla statusu eksportu paczki faktur.
     */
    @JsonProperty("invoiceExportStatus")
    private InvoiceExportStatusRateLimit invoiceStatusExport;

    /**
     * Limity dla pobierania faktur po numerze KSeF.
     */
    private InvoiceDownloadRateLimit invoiceDownload;

    /**
     * Limity dla pozostałych operacji API.
     */
    private OtherRateLimit other;

    /**
     * Limity dla identyfikatorów zbiorczych
     */
    private CollectiveIdentifierRateLimit collectiveIdentifier;

    /**
     * Limity anonimowych (nieuwierzytelnionych) operacji API.
     */
    private AnonymousRateLimit anonymous;

    /**
     * Limity globalne API naliczane per adres IP. Mechanizm może być wyłączony (wartości -1).
     */
    private GlobalRateLimit global;

    public GetRateLimitResponse() {
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
     * Limity dla zamykania sesji interaktywnych.
     */
    public OnlineSessionRateLimit getOnlineSessionClose() {
        return onlineSessionClose;
    }

    /**
     * Limity dla zamykania sesji interaktywnych.
     */
    public void setOnlineSessionClose(OnlineSessionRateLimit onlineSessionClose) {
        this.onlineSessionClose = onlineSessionClose;
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
     * Limity dla zamykania sesji wsadowych.
     */
    public BatchSessionRateLimit getBatchSessionClose() {
        return batchSessionClose;
    }

    /**
     * Limity dla zamykania sesji wsadowych.
     */
    public void setBatchSessionClose(BatchSessionRateLimit batchSessionClose) {
        this.batchSessionClose = batchSessionClose;
    }

    /**
     * Limity dla wysyłki faktur.
     */
    public InvoiceSendRateLimit getInvoiceSend() {
        return invoiceSend;
    }

    /**
     * Limity dla wysyłki faktur.
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
     * Limity dla pobierania listy sesji.
     */
    public SessionListRateLimit getSessionList() {
        return sessionList;
    }

    /**
     * Limity dla pobierania listy sesji.
     */
    public void setSessionList(SessionListRateLimit sessionList) {
        this.sessionList = sessionList;
    }

    /**
     * Limity dla pobierania listy faktur w sesji.
     */
    public SessionInvoiceListRateLimit getSessionInvoiceList() {
        return sessionInvoiceList;
    }

    /**
     * Limity dla pobierania listy faktur w sesji.
     */
    public void setSessionInvoiceList(SessionInvoiceListRateLimit sessionInvoiceList) {
        this.sessionInvoiceList = sessionInvoiceList;
    }

    /**
     * Limity dla pozostałych operacji w ramach sesji.
     */
    public SessionMiscRateLimits getSessionMisc() {
        return sessionMisc;
    }

    /**
     * Limity dla pozostałych operacji w ramach sesji.
     */
    public void setSessionMisc(SessionMiscRateLimits sessionMisc) {
        this.sessionMisc = sessionMisc;
    }

    /**
     * Limity dla pobierania metadanych faktur.
     */
    public InvoiceMetadataRateLimit getInvoiceMetadata() {
        return invoiceMetadata;
    }

    /**
     * Limity dla pobierania metadanych faktur.
     */
    public void setInvoiceMetadata(InvoiceMetadataRateLimit invoiceMetadata) {
        this.invoiceMetadata = invoiceMetadata;
    }

    /**
     * Limity dla eksportu paczki faktur.
     */
    public InvoiceExportRateLimit getInvoiceExport() {
        return invoiceExport;
    }

    /**
     * Limity dla eksportu paczki faktur.
     */
    public void setInvoiceExport(InvoiceExportRateLimit invoiceExport) {
        this.invoiceExport = invoiceExport;
    }

    /**
     * Limity dla statusu eksportu paczki faktur.
     */
    public InvoiceExportStatusRateLimit getInvoiceStatusExport() {
        return invoiceStatusExport;
    }

    /**
     * Limity dla statusu eksportu paczki faktur.
     */
    public void setInvoiceStatusExport(InvoiceExportStatusRateLimit invoiceStatusExport) {
        this.invoiceStatusExport = invoiceStatusExport;
    }

    /**
     * Limity dla pobierania faktur po numerze KSeF.
     */
    public InvoiceDownloadRateLimit getInvoiceDownload() {
        return invoiceDownload;
    }

    /**
     * Limity dla pobierania faktur po numerze KSeF.
     */
    public void setInvoiceDownload(InvoiceDownloadRateLimit invoiceDownload) {
        this.invoiceDownload = invoiceDownload;
    }

    /**
     * Limity dla pozostałych operacji API.
     */
    public OtherRateLimit getOther() {
        return other;
    }

    /**
     * Limity dla pozostałych operacji API.
     */
    public void setOther(OtherRateLimit other) {
        this.other = other;
    }

    /**
     * Limity dla identyfikatorów zbiorczych
     */
    public CollectiveIdentifierRateLimit getCollectiveIdentifier() {
        return collectiveIdentifier;
    }

    /**
     * Limity dla identyfikatorów zbiorczych
     */
    public void setCollectiveIdentifier(CollectiveIdentifierRateLimit collectiveIdentifier) {
        this.collectiveIdentifier = collectiveIdentifier;
    }

    /**
     * Limity anonimowych (nieuwierzytelnionych) operacji API.
     */
    public AnonymousRateLimit getAnonymous() {
        return anonymous;
    }

    /**
     * Limity anonimowych (nieuwierzytelnionych) operacji API.
     */
    public void setAnonymous(AnonymousRateLimit anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * Limity globalne API naliczane per adres IP. Mechanizm może być wyłączony (wartości -1).
     */
    public GlobalRateLimit getGlobal() {
        return global;
    }

    /**
     * Limity globalne API naliczane per adres IP. Mechanizm może być wyłączony (wartości -1).
     */
    public void setGlobal(GlobalRateLimit global) {
        this.global = global;
    }
}

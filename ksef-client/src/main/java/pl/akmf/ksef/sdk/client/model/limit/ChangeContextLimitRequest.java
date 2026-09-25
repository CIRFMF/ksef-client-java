package pl.akmf.ksef.sdk.client.model.limit;

/**
 * ChangeContextLimitRequest.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code SetSessionLimitsRequest}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class ChangeContextLimitRequest {

    /**
     * Limity dla sesji interaktywnych.
     */
    private OnlineSessionLimit onlineSession;

    /**
     * Limity dla sesji wsadowych.
     */
    private BatchSessionLimit batchSession;

    /**
     * Limity dla identyfikatorów zbiorczych.
     */
    private CollectiveIdentifierSessionLimits collectiveIdentifier;

    public ChangeContextLimitRequest() {
    }

    public ChangeContextLimitRequest(OnlineSessionLimit onlineSession, BatchSessionLimit batchSession, CollectiveIdentifierSessionLimits collectiveIdentifier) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
        this.collectiveIdentifier = collectiveIdentifier;
    }

    /**
     * Limity dla sesji interaktywnych.
     */
    public OnlineSessionLimit getOnlineSession() {
        return onlineSession;
    }

    /**
     * Limity dla sesji interaktywnych.
     */
    public void setOnlineSession(OnlineSessionLimit onlineSession) {
        this.onlineSession = onlineSession;
    }

    /**
     * Limity dla sesji wsadowych.
     */
    public BatchSessionLimit getBatchSession() {
        return batchSession;
    }

    /**
     * Limity dla sesji wsadowych.
     */
    public void setBatchSession(BatchSessionLimit batchSession) {
        this.batchSession = batchSession;
    }

    /**
     * Limity dla identyfikatorów zbiorczych.
     */
    public CollectiveIdentifierSessionLimits getCollectiveIdentifier() {
        return collectiveIdentifier;
    }

    /**
     * Limity dla identyfikatorów zbiorczych.
     */
    public void setCollectiveIdentifier(CollectiveIdentifierSessionLimits collectiveIdentifier) {
        this.collectiveIdentifier = collectiveIdentifier;
    }
}

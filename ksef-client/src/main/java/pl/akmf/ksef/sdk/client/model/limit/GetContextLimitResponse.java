package pl.akmf.ksef.sdk.client.model.limit;

public class GetContextLimitResponse {
    private OnlineSessionLimit onlineSession;
    private BatchSessionLimit batchSession;
    private CollectiveIdentifierSessionLimits collectiveIdentifier;

    public GetContextLimitResponse() {
    }

    public GetContextLimitResponse(OnlineSessionLimit onlineSession, BatchSessionLimit batchSession) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
    }

    public GetContextLimitResponse(OnlineSessionLimit onlineSession, BatchSessionLimit batchSession, CollectiveIdentifierSessionLimits collectiveIdentifier) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
        this.collectiveIdentifier = collectiveIdentifier;
    }

    public OnlineSessionLimit getOnlineSession() {
        return onlineSession;
    }

    public void setOnlineSession(OnlineSessionLimit onlineSession) {
        this.onlineSession = onlineSession;
    }

    public BatchSessionLimit getBatchSession() {
        return batchSession;
    }

    public void setBatchSession(BatchSessionLimit batchSession) {
        this.batchSession = batchSession;
    }

    public CollectiveIdentifierSessionLimits getCollectiveIdentifier() {
        return collectiveIdentifier;
    }

    public void setCollectiveIdentifier(CollectiveIdentifierSessionLimits collectiveIdentifier) {
        this.collectiveIdentifier = collectiveIdentifier;
    }
}

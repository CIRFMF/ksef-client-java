package pl.akmf.ksef.sdk.client.model.limit;

public class GetContextLimitResponse {
    private OnlineSessionLimit onlineSession;
    private BatchSessionLimit batchSession;

    public GetContextLimitResponse() {

    }

    public GetContextLimitResponse(OnlineSessionLimit onlineSession, BatchSessionLimit batchSession) {
        this.onlineSession = onlineSession;
        this.batchSession = batchSession;
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
}

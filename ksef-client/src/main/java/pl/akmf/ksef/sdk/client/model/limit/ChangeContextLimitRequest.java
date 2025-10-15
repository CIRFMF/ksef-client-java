package pl.akmf.ksef.sdk.client.model.limit;

public class ChangeContextLimitRequest {
    private OnlineSessionLimit onlineSession;
    private BatchSessionLimit batchSession;

    public ChangeContextLimitRequest() {

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

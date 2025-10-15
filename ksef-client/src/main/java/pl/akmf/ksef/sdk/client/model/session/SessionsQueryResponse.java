package pl.akmf.ksef.sdk.client.model.session;

import java.util.ArrayList;
import java.util.List;

public class SessionsQueryResponse {
    private String continuationToken;
    private List<SessionsQueryResponseItem> sessions = new ArrayList<>();

    public SessionsQueryResponse() {
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<SessionsQueryResponseItem> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionsQueryResponseItem> sessions) {
        this.sessions = sessions;
    }
}


package pl.akmf.ksef.sdk.client.model.session;

import java.util.ArrayList;
import java.util.List;


public class AuthenticationListResponse {
    private String continuationToken;
    private List<AuthenticationListItem> items = new ArrayList<>();

    public AuthenticationListResponse() {
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<AuthenticationListItem> getItems() {
        return items;
    }

    public void setItems(List<AuthenticationListItem> items) {
        this.items = items;
    }
}


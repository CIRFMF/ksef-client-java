package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

public class CollectiveIdentifiersQueryResponse {

    private String continuationToken;
    private List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers;

    public CollectiveIdentifiersQueryResponse() {
    }

    public CollectiveIdentifiersQueryResponse(String continuationToken, List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers) {
        this.continuationToken = continuationToken;
        this.collectiveIdentifiers = collectiveIdentifiers;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<CollectiveIdentifiersQueryResponseItem> getCollectiveIdentifiers() {
        return collectiveIdentifiers;
    }

    public void setCollectiveIdentifiers(List<CollectiveIdentifiersQueryResponseItem> collectiveIdentifiers) {
        this.collectiveIdentifiers = collectiveIdentifiers;
    }
}

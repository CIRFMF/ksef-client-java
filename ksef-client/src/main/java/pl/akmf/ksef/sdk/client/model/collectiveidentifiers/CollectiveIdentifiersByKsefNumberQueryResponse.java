package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.util.List;

public class CollectiveIdentifiersByKsefNumberQueryResponse {

    private String continuationToken;
    private List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers;

    public CollectiveIdentifiersByKsefNumberQueryResponse() {
    }

    public CollectiveIdentifiersByKsefNumberQueryResponse(String continuationToken, List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers) {
        this.continuationToken = continuationToken;
        this.collectiveIdentifiers = collectiveIdentifiers;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public List<CollectiveIdentifiersByKsefNumberQueryResponseItem> getCollectiveIdentifiers() {
        return collectiveIdentifiers;
    }

    public void setCollectiveIdentifiers(List<CollectiveIdentifiersByKsefNumberQueryResponseItem> collectiveIdentifiers) {
        this.collectiveIdentifiers = collectiveIdentifiers;
    }
}

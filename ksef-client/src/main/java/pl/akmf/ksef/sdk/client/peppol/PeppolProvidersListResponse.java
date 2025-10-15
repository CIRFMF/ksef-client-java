package pl.akmf.ksef.sdk.client.peppol;

import java.util.List;

public class PeppolProvidersListResponse {
    private List<PeppolProvider> peppolProviders;
    private Boolean hasMore;

    public PeppolProvidersListResponse() {

    }

    public List<PeppolProvider> getPeppolProviders() {
        return peppolProviders;
    }

    public void setPeppolProviders(List<PeppolProvider> peppolProviders) {
        this.peppolProviders = peppolProviders;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}

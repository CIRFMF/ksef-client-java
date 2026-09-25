package pl.akmf.ksef.sdk.client.model.session;

import java.util.List;

/**
 * UpoResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class UpoResponse {

    /**
     * Lista stron UPO.
     */
    private List<UpoPageResponse> pages;

    public UpoResponse() {
    }

    public UpoResponse(List<UpoPageResponse> pages) {
        this.pages = pages;
    }

    /**
     * Lista stron UPO.
     */
    public List<UpoPageResponse> getPages() {
        return pages;
    }

    /**
     * Lista stron UPO.
     */
    public void setPages(List<UpoPageResponse> pages) {
        this.pages = pages;
    }
}

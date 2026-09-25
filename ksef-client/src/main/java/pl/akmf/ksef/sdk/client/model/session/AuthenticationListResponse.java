package pl.akmf.ksef.sdk.client.model.session;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthenticationListResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationListResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista sesji uwierzytelniania.
     */
    private List<AuthenticationListItem> items = new ArrayList<>();

    public AuthenticationListResponse() {
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public String getContinuationToken() {
        return continuationToken;
    }

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    /**
     * Lista sesji uwierzytelniania.
     */
    public List<AuthenticationListItem> getItems() {
        return items;
    }

    /**
     * Lista sesji uwierzytelniania.
     */
    public void setItems(List<AuthenticationListItem> items) {
        this.items = items;
    }
}

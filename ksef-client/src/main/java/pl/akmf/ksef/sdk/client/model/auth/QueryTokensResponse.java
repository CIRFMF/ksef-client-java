package pl.akmf.ksef.sdk.client.model.auth;

import java.util.List;

/**
 * QueryTokensResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class QueryTokensResponse {

    /**
     * Token służący do pobrania kolejnej strony wyników. Jeśli jest pusty, to nie ma kolejnych stron.
     */
    private String continuationToken;

    /**
     * Lista tokenów uwierzytelniających.
     */
    private List<AuthenticationToken> tokens;

    public QueryTokensResponse() {
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
     * Lista tokenów uwierzytelniających.
     */
    public List<AuthenticationToken> getTokens() {
        return tokens;
    }

    /**
     * Lista tokenów uwierzytelniających.
     */
    public void setTokens(List<AuthenticationToken> tokens) {
        this.tokens = tokens;
    }
}

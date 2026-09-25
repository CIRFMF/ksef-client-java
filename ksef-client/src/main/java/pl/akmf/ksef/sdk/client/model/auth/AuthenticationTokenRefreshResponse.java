package pl.akmf.ksef.sdk.client.model.auth;

/**
 * AuthenticationTokenRefreshResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationTokenRefreshResponse {

    /**
     * Token dostępu, którego należy używać w wywołaniach chronionych zasobów API.
     */
    private TokenInfo accessToken;

    public AuthenticationTokenRefreshResponse() {
    }

    public AuthenticationTokenRefreshResponse(TokenInfo accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Token dostępu, którego należy używać w wywołaniach chronionych zasobów API.
     */
    public TokenInfo getAccessToken() {
        return accessToken;
    }

    /**
     * Token dostępu, którego należy używać w wywołaniach chronionych zasobów API.
     */
    public void setAccessToken(TokenInfo accessToken) {
        this.accessToken = accessToken;
    }
}

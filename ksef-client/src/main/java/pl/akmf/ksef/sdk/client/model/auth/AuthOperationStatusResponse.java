package pl.akmf.ksef.sdk.client.model.auth;

/**
 * AuthOperationStatusResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AuthenticationTokensResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthOperationStatusResponse {

    /**
     * Token dostępu.
     */
    private TokenInfo accessToken;

    /**
     * Token umożliwiający odświeżenie tokenu dostępu. &gt; Więcej informacji: &gt; - <a href="https://github.com/CIRFMF/ksef-api/blob/main/uwierzytelnianie.md#5-od%C5%9Bwie%C5%BCenie-tokena-dost%C4%99powego-accesstoken">Odświeżanie tokena</a>
     */
    private TokenInfo refreshToken;

    public AuthOperationStatusResponse() {
    }

    public AuthOperationStatusResponse(TokenInfo accessToken, TokenInfo refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * Token dostępu.
     */
    public TokenInfo getAccessToken() {
        return accessToken;
    }

    /**
     * Token dostępu.
     */
    public void setAccessToken(TokenInfo accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Token umożliwiający odświeżenie tokenu dostępu. &gt; Więcej informacji: &gt; - <a href="https://github.com/CIRFMF/ksef-api/blob/main/uwierzytelnianie.md#5-od%C5%9Bwie%C5%BCenie-tokena-dost%C4%99powego-accesstoken">Odświeżanie tokena</a>
     */
    public TokenInfo getRefreshToken() {
        return refreshToken;
    }

    /**
     * Token umożliwiający odświeżenie tokenu dostępu. &gt; Więcej informacji: &gt; - <a href="https://github.com/CIRFMF/ksef-api/blob/main/uwierzytelnianie.md#5-od%C5%9Bwie%C5%BCenie-tokena-dost%C4%99powego-accesstoken">Odświeżanie tokena</a>
     */
    public void setRefreshToken(TokenInfo refreshToken) {
        this.refreshToken = refreshToken;
    }
}

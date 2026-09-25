package pl.akmf.ksef.sdk.client.model.auth;

/**
 * SignatureResponse.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AuthenticationInitResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class SignatureResponse {

    /**
     * Numer referencyjny sesji uwierzytelnienia.
     */
    private String referenceNumber;

    /**
     * Token operacji uwierzytelnienia.
     */
    private TokenInfo authenticationToken;

    public SignatureResponse() {
    }

    public SignatureResponse(String referenceNumber, TokenInfo authenticationToken) {
        this.referenceNumber = referenceNumber;
        this.authenticationToken = authenticationToken;
    }

    /**
     * Numer referencyjny sesji uwierzytelnienia.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny sesji uwierzytelnienia.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Token operacji uwierzytelnienia.
     */
    public TokenInfo getAuthenticationToken() {
        return authenticationToken;
    }

    /**
     * Token operacji uwierzytelnienia.
     */
    public void setAuthenticationToken(TokenInfo authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}

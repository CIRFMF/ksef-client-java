package pl.akmf.ksef.sdk.client.model.auth;

/**
 * GenerateTokenResponse.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class GenerateTokenResponse {

    /**
     * Numer referencyjny tokena KSeF.
     */
    private String referenceNumber;

    /**
     * Token KSeF.
     */
    private String token;

    public GenerateTokenResponse() {
    }

    /**
     * Numer referencyjny tokena KSeF.
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Numer referencyjny tokena KSeF.
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Token KSeF.
     */
    public String getToken() {
        return token;
    }

    /**
     * Token KSeF.
     */
    public void setToken(String token) {
        this.token = token;
    }
}

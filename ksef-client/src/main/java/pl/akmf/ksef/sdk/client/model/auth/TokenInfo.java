package pl.akmf.ksef.sdk.client.model.auth;

import java.time.OffsetDateTime;

/**
 * TokenInfo.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class TokenInfo {

    /**
     * Token w formacie JWT.
     */
    private String token;

    /**
     * Data ważności tokena.
     */
    private OffsetDateTime validUntil;

    public TokenInfo() {
    }

    public TokenInfo(String token, OffsetDateTime validUntil) {
        this.token = token;
        this.validUntil = validUntil;
    }

    /**
     * Token w formacie JWT.
     */
    public String getToken() {
        return token;
    }

    /**
     * Token w formacie JWT.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Data ważności tokena.
     */
    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Data ważności tokena.
     */
    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }
}

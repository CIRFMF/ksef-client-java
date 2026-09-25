package pl.akmf.ksef.sdk.client.model.auth;

import pl.akmf.ksef.sdk.client.model.StatusInfo;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationMethod;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationMethodInfo;
import java.time.OffsetDateTime;

/**
 * AuthStatus.
 *
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code AuthenticationOperationStatusResponse}.
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthStatus {

    /**
     * Data rozpoczęcia operacji uwierzytelnienia.
     */
    private OffsetDateTime startDate;

    /**
     * Metoda uwierzytelnienia.
     */
    @Deprecated(since = "planowane wycofanie: 2026-11-16")
    private AuthenticationMethod authenticationMethod;

    /**
     * Użyta metoda uwierzytelnienia.
     */
    private AuthenticationMethodInfo authenticationMethodInfo;

    /**
     * Informacje o aktualnym statusie.
     */
    private StatusInfo status;

    /**
     * Czy został już wydany refresh token powiązany z danym uwierzytelnieniem.
     */
    private Boolean isTokenRedeemed;

    /**
     * Data ostatniego odświeżenia tokena.
     */
    private OffsetDateTime lastTokenRefreshDate;

    /**
     * Termin ważności refresh tokena (o ile nie zostanie wcześniej unieważniony).
     */
    private OffsetDateTime refreshTokenValidUntil;

    public AuthStatus() {
    }

    /**
     * Data rozpoczęcia operacji uwierzytelnienia.
     */
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    /**
     * Data rozpoczęcia operacji uwierzytelnienia.
     */
    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Metoda uwierzytelnienia.
     */
    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * Metoda uwierzytelnienia.
     */
    public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    /**
     * Użyta metoda uwierzytelnienia.
     */
    public AuthenticationMethodInfo getAuthenticationMethodInfo() {
        return authenticationMethodInfo;
    }

    /**
     * Użyta metoda uwierzytelnienia.
     */
    public void setAuthenticationMethodInfo(AuthenticationMethodInfo authenticationMethodInfo) {
        this.authenticationMethodInfo = authenticationMethodInfo;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * Informacje o aktualnym statusie.
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * Czy został już wydany refresh token powiązany z danym uwierzytelnieniem.
     */
    public Boolean getIsTokenRedeemed() {
        return isTokenRedeemed;
    }

    /**
     * Czy został już wydany refresh token powiązany z danym uwierzytelnieniem.
     */
    public void setIsTokenRedeemed(Boolean tokenRedeemed) {
        isTokenRedeemed = tokenRedeemed;
    }

    /**
     * Data ostatniego odświeżenia tokena.
     */
    public OffsetDateTime getLastTokenRefreshDate() {
        return lastTokenRefreshDate;
    }

    /**
     * Data ostatniego odświeżenia tokena.
     */
    public void setLastTokenRefreshDate(OffsetDateTime lastTokenRefreshDate) {
        this.lastTokenRefreshDate = lastTokenRefreshDate;
    }

    /**
     * Termin ważności refresh tokena (o ile nie zostanie wcześniej unieważniony).
     */
    public OffsetDateTime getRefreshTokenValidUntil() {
        return refreshTokenValidUntil;
    }

    /**
     * Termin ważności refresh tokena (o ile nie zostanie wcześniej unieważniony).
     */
    public void setRefreshTokenValidUntil(OffsetDateTime refreshTokenValidUntil) {
        this.refreshTokenValidUntil = refreshTokenValidUntil;
    }
}

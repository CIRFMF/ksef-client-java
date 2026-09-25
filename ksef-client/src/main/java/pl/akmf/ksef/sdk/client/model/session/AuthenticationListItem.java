package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;

import java.time.OffsetDateTime;

/**
 * AuthenticationListItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthenticationListItem {

    /**
     * Numer referencyjny sesji uwierzytelnienia.
     */
    private String referenceNumber;

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
     * Czy sesja jest powiązana z aktualnie używanym tokenem.
     */
    private Boolean isCurrent;

    /**
     * Data ostatniego odświeżenia tokena.
     */
    private OffsetDateTime lastTokenRefreshDate;

    /**
     * Termin ważności refresh tokena (o ile nie zostanie wcześniej unieważniony).
     */
    private OffsetDateTime refreshTokenValidUntil;

    public AuthenticationListItem() {
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
     * Czy sesja jest powiązana z aktualnie używanym tokenem.
     */
    public Boolean getIsCurrent() {
        return isCurrent;
    }

    /**
     * Czy sesja jest powiązana z aktualnie używanym tokenem.
     */
    public void setIsCurrent(Boolean current) {
        isCurrent = current;
    }

    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

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

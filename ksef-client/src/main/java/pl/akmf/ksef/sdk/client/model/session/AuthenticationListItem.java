package pl.akmf.ksef.sdk.client.model.session;

import pl.akmf.ksef.sdk.client.model.StatusInfo;

import java.time.OffsetDateTime;

public class AuthenticationListItem {
    private String referenceNumber;

    private OffsetDateTime startDate;

    @Deprecated(since = "planowane wycofanie: 2026-11-16")
    private AuthenticationMethod authenticationMethod;

    private AuthenticationMethodInfo authenticationMethodInfo;

    private StatusInfo status;

    private Boolean isTokenRedeemed;

    private Boolean isCurrent;

    private OffsetDateTime lastTokenRefreshDate;

    private OffsetDateTime refreshTokenValidUntil;

    public AuthenticationListItem() {
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean current) {
        isCurrent = current;
    }

    public AuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public AuthenticationMethodInfo getAuthenticationMethodInfo() {
        return authenticationMethodInfo;
    }

    public void setAuthenticationMethodInfo(AuthenticationMethodInfo authenticationMethodInfo) {
        this.authenticationMethodInfo = authenticationMethodInfo;
    }

    public StatusInfo getStatus() {
        return status;
    }

    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    public Boolean getIsTokenRedeemed() {
        return isTokenRedeemed;
    }

    public void setIsTokenRedeemed(Boolean tokenRedeemed) {
        isTokenRedeemed = tokenRedeemed;
    }

    public OffsetDateTime getLastTokenRefreshDate() {
        return lastTokenRefreshDate;
    }

    public void setLastTokenRefreshDate(OffsetDateTime lastTokenRefreshDate) {
        this.lastTokenRefreshDate = lastTokenRefreshDate;
    }

    public OffsetDateTime getRefreshTokenValidUntil() {
        return refreshTokenValidUntil;
    }

    public void setRefreshTokenValidUntil(OffsetDateTime refreshTokenValidUntil) {
        this.refreshTokenValidUntil = refreshTokenValidUntil;
    }
}

package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.ExceptionDetails;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationListItem;
import pl.akmf.ksef.sdk.client.model.session.AuthenticationListResponse;
import pl.akmf.ksef.sdk.client.model.session.CommonSessionStatus;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionType;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionsQueryResponseItem;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void createSessionSearchSessionAndRevokeCurrentSession() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair accessTokensPair = authWithCustomNip(contextNip, contextNip);
        String accessToken = accessTokensPair.accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        // Step 1: Open session and return referenceNumber
        openOnlineSession(encryptionData, accessToken);

        // Step 2: get active sessions and check quantity
        AuthenticationListResponse activeSessions = ksefClient.getActiveSessions(10, null, accessToken);
        while (Strings.isNotBlank(activeSessions.getContinuationToken())) {
            activeSessions = ksefClient.getActiveSessions(10, activeSessions.getContinuationToken(), accessToken);
        }

        Assertions.assertEquals(1, activeSessions.getItems().size());
        Assertions.assertEquals(true, activeSessions.getItems().getFirst().getIsCurrent());

        // Step 3: revoke current session
        ksefClient.revokeCurrentSession(accessToken);

        // Step 4: get active sessions and check quantity after revoked current session
        AuthenticationListResponse activeSessionsAfterRevoke = ksefClient.getActiveSessions(10, null, accessToken);
        Assertions.assertEquals(0, activeSessionsAfterRevoke.getItems().size());
        while (Strings.isNotBlank(activeSessionsAfterRevoke.getContinuationToken())) {
            activeSessionsAfterRevoke = ksefClient.getActiveSessions(10, activeSessionsAfterRevoke.getContinuationToken(), accessToken);
        }

        // Step 5: refresh token should throw: 21304: Brak uwierzytelnienia. - Nieprawidłowy token.
        ApiException apiException = assertThrows(ApiException.class, () -> ksefClient.refreshAccessToken(accessTokensPair.refreshToken()));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21304, details.getExceptionCode());
        Assertions.assertEquals("Brak uwierzytelnienia.", details.getExceptionDescription());
        Assertions.assertEquals("Nieprawidłowy token.", details.getDetails().getFirst());
    }

    @Test
    void createSecondSessionAndRevokeSessionByReferenceNumber() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair firstAccessTokensPair = authWithCustomNip(contextNip, contextNip);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        // Step 1: Open session and return referenceNumber
        openOnlineSession(encryptionData, firstAccessTokensPair.accessToken());

        // Step 2: get active sessions and check quantity
        AuthenticationListResponse activeSessions = ksefClient.getActiveSessions(10, null, firstAccessTokensPair.accessToken());
        Assertions.assertEquals(1, activeSessions.getItems().size());
        while (Strings.isNotBlank(activeSessions.getContinuationToken())) {
            activeSessions = ksefClient.getActiveSessions(10, activeSessions.getContinuationToken(), firstAccessTokensPair.accessToken());
        }
        String firstSessionReferenceNumber = activeSessions.getItems().get(0).getReferenceNumber();

        AuthTokensPair secondAccessTokensPair = authWithCustomNip(contextNip, contextNip);

        // Step 3: get active sessions after second auth and check quantity
        activeSessions = ksefClient.getActiveSessions(10, null, firstAccessTokensPair.accessToken());
        while (Strings.isNotBlank(activeSessions.getContinuationToken())) {
            activeSessions = ksefClient.getActiveSessions(10, activeSessions.getContinuationToken(), firstAccessTokensPair.accessToken());
        }
        Assertions.assertEquals(2, activeSessions.getItems().size());
        String secondSessionReferenceNumber = activeSessions.getItems()
                .stream()
                .filter(s -> !s.getIsCurrent())
                .map(AuthenticationListItem::getReferenceNumber)
                .findFirst()
                .orElseThrow();

        // first session is current
        Assertions.assertTrue(activeSessions.getItems()
                .stream()
                .filter(s -> s.getReferenceNumber().equals(firstSessionReferenceNumber))
                .map(AuthenticationListItem::getIsCurrent)
                .findFirst()
                .orElseThrow());

        // Step 4: revoke second session by reference number
        ksefClient.revokeSession(secondSessionReferenceNumber, firstAccessTokensPair.accessToken());

        // Step 5: get active sessions and check quantity after revoked second session
        AuthenticationListResponse activeSessionsAfterRevoke = ksefClient.getActiveSessions(10, null, firstAccessTokensPair.accessToken());
        Assertions.assertEquals(1, activeSessionsAfterRevoke.getItems().size());
        while (Strings.isNotBlank(activeSessionsAfterRevoke.getContinuationToken())) {
            activeSessionsAfterRevoke = ksefClient.getActiveSessions(10, activeSessionsAfterRevoke.getContinuationToken(), firstAccessTokensPair.accessToken());
        }

        // Step 6: refresh token should throw: 21304: Brak uwierzytelnienia. - Nieprawidłowy token.
        ApiException apiException = assertThrows(ApiException.class, () -> ksefClient.refreshAccessToken(secondAccessTokensPair.refreshToken()));
    }

    @Test
    void searchSessionsByStatuses() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        AuthTokensPair firstAccessTokensPair = authWithCustomNip(contextNip, contextNip);
        AuthTokensPair secondAccessTokensPair = authWithCustomNip(contextNip, contextNip);

        String firstSessionRefNumber = openOnlineSession(encryptionData, firstAccessTokensPair.accessToken());
        String secondSessionRefNumber = openOnlineSession(encryptionData, secondAccessTokensPair.accessToken());

        ksefClient.closeOnlineSession(secondSessionRefNumber, firstAccessTokensPair.accessToken());

        SessionsQueryRequest request = new SessionsQueryRequest();
        request.setSessionType(SessionType.ONLINE);
        request.setStatuses(List.of(CommonSessionStatus.INPROGRESS, CommonSessionStatus.CANCELLED));
        SessionsQueryResponse sessionsQueryResponse = ksefClient.getSessions(request, 10, null, firstAccessTokensPair.accessToken());
        Assertions.assertEquals(2, sessionsQueryResponse.getSessions().size());
        SessionsQueryResponseItem firstSession = sessionsQueryResponse.getSessions().stream()
                .filter(s -> s.getReferenceNumber().equals(firstSessionRefNumber))
                .findFirst()
                .orElseThrow();
        SessionsQueryResponseItem secondSession = sessionsQueryResponse.getSessions().stream()
                .filter(s -> s.getReferenceNumber().equals(secondSessionRefNumber))
                .findFirst()
                .orElseThrow();
        Assertions.assertEquals(100, firstSession.getStatus().getCode());
        Assertions.assertEquals("Sesja interaktywna otwarta", firstSession.getStatus().getDescription());
        Assertions.assertEquals(440, secondSession.getStatus().getCode());
        Assertions.assertEquals("Sesja anulowana", secondSession.getStatus().getDescription());
    }

    @Test
    void searchSessions() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        // Step 1: Open session and return referenceNumber
        openOnlineSession(encryptionData, accessToken);

        // Step 2: Search session
        SessionsQueryRequest request = new SessionsQueryRequest();
        request.setSessionType(SessionType.ONLINE);
        request.setStatuses(List.of(CommonSessionStatus.INPROGRESS));
        SessionsQueryResponse sessionsQueryResponse = ksefClient.getSessions(request, 10, null, accessToken);
        Assertions.assertEquals(1, sessionsQueryResponse.getSessions().size());
    }

    private String openOnlineSession(EncryptionData encryptionData, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }
}

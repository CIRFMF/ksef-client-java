package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.auth.AuthKsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.PersonPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.tokens.KsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.KsefApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.auth.AuthKsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationToken;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenRefreshResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenStatus;
import pl.akmf.ksef.sdk.client.model.auth.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.auth.GenerateTokenResponse;
import pl.akmf.ksef.sdk.client.model.auth.KsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.auth.TokenPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntityPersonByIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntitySubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntitySubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonById;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionQueryType;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RevokedPermissionsKsefTokenLifecycleIntegrationTests extends BaseIntegrationTest {

    private static final String grantDescription = "E2E grant InvoiceWrite+InvoiceRead for KSeF token lifecycle test";

    // Właściciel nadaje pracownikowi uprawnienia i odbiera je dopiero po tym, jak pracownik wygenerował
    // sobie token KSeF i uwierzytelnił się nim, uzyskując parę access/refresh token.
    //
    // Oczekiwane zachowanie:
    // - dopóki wcześniej wydany access token jest ważny, pracownik może nadal wykonywać operacje
    //   wymagające odebranych uprawnień (uprawnienia są "zamrożone" w już wydanym tokenie),
    // - odświeżenie access tokena (refresh token) zwraca token bez uprawnień - kolejna próba operacji kończy się błędem,
    // - ponowne uwierzytelnienie tym samym tokenem KSeF od zera kończy się błędem już na etapie uwierzytelnienia,
    //   ponieważ token nie ma już żadnych aktywnych uprawnień.
    @Test
    void revokedPermissions_OldAccessTokenStillWorks_ButRefreshAndReauthenticationLoseAccess() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String employeeNip = IdentifierGeneratorUtils.generateRandomNIP();

        // Właściciel uwierzytelnia się i nadaje pracownikowi InvoiceWrite oraz InvoiceRead
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        PermissionsIndirectEntitySubjectDetails subjectDetails = new PermissionsIndirectEntitySubjectDetails();
        subjectDetails.setSubjectDetailsType(PermissionsIndirectEntitySubjectDetailsType.PersonByIdentifier);
        subjectDetails.setPersonById(new PermissionsIndirectEntityPersonByIdentifier("Test", "Ttest"));
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, employeeNip))
                .withPermissions(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .withDescription(grantDescription)
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        OperationResponse response = ksefClient.grantsPermissionPerson(request, ownerAccessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(response.getReferenceNumber(), ownerAccessToken));

        // Pracownik uwierzytelnia się (certyfikatem) w kontekście właściciela - pierwsze logowanie,
        // potrzebne wyłącznie po to, by móc wygenerować sobie własny token KSeF
        String employeeCertAccessToken = authWithCustomNip(ownerNip, employeeNip).accessToken();

        // Pracownik generuje sobie token KSeF w kontekście właściciela i czeka na jego aktywację
        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("E2E employee KSeF token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse ksefTokenResponse = ksefClient.generateKsefToken(createTokenRequest, employeeCertAccessToken);
        Assertions.assertNotNull(ksefTokenResponse.getReferenceNumber());
        Assertions.assertNotNull(ksefTokenResponse.getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(ksefTokenResponse, employeeCertAccessToken));

        // Pracownik uwierzytelnia się tokenem KSeF w kontekście właściciela - to ten dostęp będzie testowany
        AuthOperationStatusResponse employeeTokenAuth = authenticateWithKsefToken(ksefTokenResponse.getToken(), ownerNip);
        Assertions.assertNotNull(employeeTokenAuth);
        Assertions.assertNotNull(employeeTokenAuth.getAccessToken());
        Assertions.assertNotNull(employeeTokenAuth.getRefreshToken());

        String employeeAccessToken = employeeTokenAuth.getAccessToken().getToken();
        String employeeRefreshToken = employeeTokenAuth.getRefreshToken().getToken();

        EncryptionData encryptionData = cryptographyService.getEncryptionData();

        // Sanity check - dzięki nadanym uprawnieniom pracownik może otworzyć sesję interaktywną
        String sessionReferenceNumberBeforeRevoke = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, employeeAccessToken);
        Assertions.assertNotNull(sessionReferenceNumberBeforeRevoke);

        // Właściciel odbiera pracownikowi wszystkie nadane uprawnienia
        revokeAllGrantedPermissions(ownerAccessToken);

        // Mimo odebrania uprawnień, dotychczas wydany access token nadal działa (uprawnienia "zamrożone" w tokenie)
        String sessionWithStillValidTokenReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, employeeAccessToken);
        Assertions.assertNotNull(sessionWithStillValidTokenReferenceNumber);

        // Odświeżenie access tokena zwraca token, który nie ma już nadanych uprawnień
        AuthenticationTokenRefreshResponse refreshedToken = ksefClient.refreshAccessToken(employeeRefreshToken);
        Assertions.assertNotNull(refreshedToken.getAccessToken());

        assertThrows(ApiException.class, () ->
                openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, refreshedToken.getAccessToken().getToken()));

        // Ponowne uwierzytelnienie od zera tym samym tokenem KSeF nie powiedzie się -
        // token nie ma już żadnych aktywnych uprawnień, więc uwierzytelnienie kończy się błędem już na tym etapie.
        assertThrows(ApiException.class, () ->
                authenticateWithKsefToken(ksefTokenResponse.getToken(), ownerNip));
    }

    // Wyszukuje uprawnienia nadane pracownikowi (po opisie grantu) w bieżącym kontekście i odwołuje każde z nich.
    private void revokeAllGrantedPermissions(String ownerAccessToken) throws ApiException {
        PersonPermissionsQueryRequest query = new PersonPermissionsQueryRequestBuilder()
                .withQueryType(PersonPermissionQueryType.PERMISSION_GRANTED_IN_CURRENT_CONTEXT)
                .withPermissionTypes(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .build();

        QueryPersonPermissionsResponse grantedPermissions = ksefClient.searchGrantedPersonPermissions(query, 0, 10, ownerAccessToken);
        Assertions.assertTrue(grantedPermissions.getPermissions().stream()
                .anyMatch(a -> a.getDescription().contains(grantDescription)));

        List<PersonPermission> toRevoke = grantedPermissions.getPermissions().stream()
                .filter(a -> a.getDescription().contains(grantDescription))
                .toList();

        Assertions.assertNotNull(toRevoke);
        Assertions.assertFalse(toRevoke.isEmpty());

        for (PersonPermission permission : toRevoke) {
            OperationResponse revoke = ksefClient.revokeCommonPermission(permission.getId(), ownerAccessToken);

            await().pollDelay(Duration.ZERO)
                    .atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isOperationFinish(revoke.getReferenceNumber(), ownerAccessToken));
        }
    }

    // Przeprowadza pełny proces uwierzytelnienia przy użyciu tokena KSeF w podanym kontekście (NIP).
    private AuthOperationStatusResponse authenticateWithKsefToken(String token, String nip) throws ApiException {
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        byte[] encryptedToken = cryptographyService.encryptKsefTokenUsingPublicKey(token, challenge.getTimestamp());

        AuthKsefTokenRequest authTokenRequest = new AuthKsefTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.NIP, nip))
                .withEncryptedToken(Base64.getEncoder().encodeToString(encryptedToken))
                .withPublicKeyId(cryptographyService.getKsefToken().getPublicKeyId())
                .build();

        SignatureResponse response = ksefClient.authenticateByKSeFToken(authTokenRequest);

        await().pollDelay(Duration.ZERO)
                .atMost(60, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    AuthStatus authStatus = ksefClient.getAuthStatus(response.getReferenceNumber(), response.getAuthenticationToken().getToken());
                    return authStatus != null && authStatus.getStatus().getCode() != 100;
                });

        AuthStatus authStatus = ksefClient.getAuthStatus(response.getReferenceNumber(), response.getAuthenticationToken().getToken());
        if (authStatus.getStatus().getCode() != 200) {
            throw new KsefApiException("Uwierzytelnienie tokenem KSeF zakończyło się niepowodzeniem: " + authStatus.getStatus().getDetails());
        }

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(response.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);

        return tokenResponse;
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private Boolean isActiveToken(GenerateTokenResponse token, String accessToken) throws ApiException {
        AuthenticationToken ksefToken = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
        return ksefToken != null && ksefToken.getStatus() == AuthenticationTokenStatus.ACTIVE;
    }

    private String openOnlineSession(EncryptionData encryptionData, SystemCode systemCode, SchemaVersion schemaVersion, SessionValue value, String accessToken) throws ApiException {
        OpenOnlineSessionRequest request = new OpenOnlineSessionRequestBuilder()
                .withFormCode(new FormCode(systemCode, schemaVersion, value))
                .withEncryptionInfo(encryptionData.encryptionInfo())
                .build();

        OpenOnlineSessionResponse openOnlineSessionResponse = ksefClient.openOnlineSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(openOnlineSessionResponse);
        Assertions.assertNotNull(openOnlineSessionResponse.getReferenceNumber());
        return openOnlineSessionResponse.getReferenceNumber();
    }
}

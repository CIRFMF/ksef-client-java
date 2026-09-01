package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.akmf.ksef.sdk.api.builders.auth.AuthKsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.tokens.KsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.auth.AuthKsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationToken;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenStatus;
import pl.akmf.ksef.sdk.client.model.auth.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.auth.GenerateTokenResponse;
import pl.akmf.ksef.sdk.client.model.auth.KsefTokenRequest;
import pl.akmf.ksef.sdk.client.model.auth.QueryTokensResponse;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.auth.TokenPermissionType;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KsefTokenIntegrationTest extends BaseIntegrationTest {

    @Test
    void checkGenerateTokenTest() throws IOException, ApiException, JAXBException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // step 1: generate tokens
        KsefTokenRequest request = new KsefTokenRequestBuilder()
                .withDescription("test description")
                .withPermissions(List.of(
                        TokenPermissionType.INVOICE_READ,
                        TokenPermissionType.INVOICE_WRITE,
                        TokenPermissionType.CREDENTIALS_READ))
                .build();

        GenerateTokenResponse token = ksefClient.generateKsefToken(request, accessToken);
        GenerateTokenResponse token2 = ksefClient.generateKsefToken(request, accessToken);
        GenerateTokenResponse token3 = ksefClient.generateKsefToken(request, accessToken);

        Assertions.assertNotNull(token);
        Assertions.assertNotNull(token.getToken());
        Assertions.assertNotNull(token.getReferenceNumber());

        // step 2: wait for tokens to become ACTIVE
        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(token, accessToken)
                        && isActiveToken(token2, accessToken)
                        && isActiveToken(token3, accessToken));

        AuthenticationToken ksefToken = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
        Assertions.assertNotNull(ksefToken);
        Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, ksefToken.getStatus());

        // step 3: filter active tokens
        List<AuthenticationTokenStatus> status = List.of(AuthenticationTokenStatus.ACTIVE);
        Integer pageSize = 10;
        QueryTokensResponse tokens = ksefClient.queryKsefTokens(status, StringUtils.EMPTY, null, null, null, pageSize, accessToken);
        List<AuthenticationToken> filteredTokens = tokens.getTokens();
        Assertions.assertNotNull(filteredTokens);
        Assertions.assertEquals(3, filteredTokens.size());

        // step 4: revoke token and wait for REVOKED status
        ksefClient.revokeKsefToken(token.getReferenceNumber(), accessToken);

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken revokedToken = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
                    return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                });

        AuthenticationToken ksefTokenAfterRevoke = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
        Assertions.assertNotNull(ksefTokenAfterRevoke);
        Assertions.assertEquals(AuthenticationTokenStatus.REVOKED, ksefTokenAfterRevoke.getStatus());

        // step 5: filter active tokens after revoking one
        QueryTokensResponse tokens2 = ksefClient.queryKsefTokens(status, StringUtils.EMPTY, null, null, null, pageSize, accessToken);
        List<AuthenticationToken> filteredTokens2 = tokens2.getTokens();
        Assertions.assertNotNull(filteredTokens2);
        Assertions.assertEquals(2, filteredTokens2.size());
    }

    static Stream<Arguments> inputTestParameters() {
        return Stream.of(
                Arguments.of(EncryptionMethod.RSA)
//                Arguments.of( EncryptionMethod.ECDSA) // [ECDSA is not supported yet]
        );
    }

    @ParameterizedTest
    @MethodSource("inputTestParameters")
    public void tokenTest(EncryptionMethod encryptionMethod) throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);
        KsefTokenRequest request = new KsefTokenRequestBuilder()
                .withDescription("test description")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse ksefToken = ksefClient.generateKsefToken(request, authToken.accessToken());
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        byte[] encryptedToken = cryptographyService.encryptKsefTokenUsingPublicKey(ksefToken.getToken(), challenge.getTimestamp());

        AuthKsefTokenRequest authTokenRequest = new AuthKsefTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.NIP, contextNip))
                .withEncryptedToken(Base64.getEncoder().encodeToString(encryptedToken))
                .withPublicKeyId(cryptographyService.getKsefToken().getPublicKeyId())
                .build();

        SignatureResponse response = ksefClient.authenticateByKSeFToken(authTokenRequest);

        await().pollDelay(Duration.ZERO)
                .atMost(60, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isAuthStatusReady(response.getReferenceNumber(), response.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(response.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);
    }

    // Test weryfikujący pełny cykl życia tokena KSeF:
    // generowanie, oczekiwanie na aktywację, uwierzytelnienie tokenem, unieważnienie oraz weryfikacja unieważnienia.
    // Kroki:
    // 1) Generuje token KSeF z uprawnieniami (InvoiceRead, InvoiceWrite).
    // 2) Czeka aż status tokena zmieni się na Active.
    // 3) Uwierzytelnia się do KSeF używając tokena (RSA-OAEP SHA-256 na ciągu "token|timestamp") i pobiera access/refresh token.
    // 4) Unieważnia wygenerowany token.
    // 5) Sprawdza czy token ma status Revoked.
    @Test
    void ksefTokens_FullIntegrationFlow_AllStepsSucceed() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);
        // 1) Wygeneruj token KSeF z uprawnieniami
        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        // 2) Poczekaj, aż token stanie się aktywny
        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));

        AuthenticationToken ksefToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
        Assertions.assertNotNull(ksefToken);
        Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, ksefToken.getStatus());

        // 3) Uwierzytelnij w KSeF przy użyciu tokena KSeF (polling statusu 200 w AuthenticateWithKsefTokenAsync)
        AuthOperationStatusResponse authResult = authenticateWithKsefToken(tokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(authResult);
        Assertions.assertNotNull(authResult.getAccessToken());
        Assertions.assertNotNull(authResult.getRefreshToken());

        // 4) Unieważnij token
        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());

        // 5) Zweryfikuj, że token został unieważniony
        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken revokedToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
                    return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                });

        AuthenticationToken ksefTokenAfterRevoke = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
        Assertions.assertNotNull(ksefTokenAfterRevoke);
        Assertions.assertEquals(AuthenticationTokenStatus.REVOKED, ksefTokenAfterRevoke.getStatus());
    }

    @Test
    void when_KsefTokensAreActive_ThenTryToRevokeOneWithAnother_ShouldThrowKsefApiException() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        // 1) Przygotuj dwa tokeny
        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        GenerateTokenResponse secondTokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(secondTokenResponse.getReferenceNumber());
        Assertions.assertNotNull(secondTokenResponse.getToken());

        // 2) Poczekaj, aż token stanie się aktywny
        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(secondTokenResponse, authToken.accessToken()));

        AuthenticationToken ksefToken = ksefClient.getKsefToken(secondTokenResponse.getReferenceNumber(), authToken.accessToken());
        Assertions.assertNotNull(ksefToken);
        Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, ksefToken.getStatus());

        // 3) Uwierzytelnij w KSeF przy użyciu drugiego tokena KSeF (polling statusu 200 w AuthenticateWithKsefTokenAsync)
        AuthOperationStatusResponse authResultSecondToken = authenticateWithKsefToken(secondTokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(authResultSecondToken);
        Assertions.assertNotNull(authResultSecondToken.getAccessToken());
        Assertions.assertNotNull(authResultSecondToken.getRefreshToken());

        // 4) Unieważnij token pierwszy tokenem drugim
        assertThrows(ApiException.class, () ->
                ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authResultSecondToken.getAccessToken().getToken()));

        // 5) Unieważnij tokeny
        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
        ksefClient.revokeKsefToken(secondTokenResponse.getReferenceNumber(), authToken.accessToken());

        // 6) Zweryfikuj, że tokeny zostały unieważnione
        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken revokedToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authResultSecondToken.getAccessToken().getToken());
                    return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                });

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken revokedToken = ksefClient.getKsefToken(secondTokenResponse.getReferenceNumber(), authResultSecondToken.getAccessToken().getToken());
                    return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                });
    }

    // Test weryfikujący, że aktywny token KSeF może unieważnić sam siebie bez uprawnienia CredentialsManage.
    // DELETE /tokens/{referenceNumber} jest dozwolone dla właściciela tokenu.
    // Kroki:
    // 1) Generuje token KSeF z uprawnieniami (InvoiceRead, InvoiceWrite) — bez CredentialsManage.
    // 2) Czeka aż status tokenu zmieni się na Active.
    // 3) Uwierzytelnia się do KSeF przy użyciu tego tokenu i pobiera jego własny access token.
    // 4) Unieważnia token używając jego własnego access tokenu (bez CredentialsManage).
    // 5) Sprawdza czy token ma status Revoked.
    @Test
    void ksefToken_SelfRevoke_WithoutCredentialsManage_ShouldSucceed() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("Self-revoke E2E token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));

        AuthOperationStatusResponse tokenAuthResult = authenticateWithKsefToken(tokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(tokenAuthResult);
        Assertions.assertNotNull(tokenAuthResult.getAccessToken().getToken());

        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), tokenAuthResult.getAccessToken().getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken revokedToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
                    return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                });
    }

    // Test weryfikujący, że token KSeF może pobrać informacje o samym sobie bez uprawnienia CredentialsManage.
    // GET /tokens/{referenceNumber} jest dozwolone dla właściciela tokenu.
    // Kroki:
    // 1) Generuje token KSeF z uprawnieniami (InvoiceRead, InvoiceWrite) — bez CredentialsManage.
    // 2) Czeka aż status tokenu zmieni się na Active.
    // 3) Uwierzytelnia się do KSeF przy użyciu tego tokenu i pobiera jego własny access token.
    // 4) Pobiera informacje o własnym tokenie używając access tokenu z kroku 3 (bez CredentialsManage).
    // 5) Weryfikuje poprawność zwróconych danych tokenu.
    @Test
    void sefToken_GetSelf_WithoutCredentialsManage_ShouldSucceed() throws ApiException, JAXBException, IOException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("Self-get E2E token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));

        AuthOperationStatusResponse tokenAuthResult = authenticateWithKsefToken(tokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(tokenAuthResult);
        Assertions.assertNotNull(tokenAuthResult.getAccessToken());

        AuthenticationToken selfToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), tokenAuthResult.getAccessToken().getToken());
        Assertions.assertNotNull(selfToken);
        Assertions.assertEquals(tokenResponse.getReferenceNumber(), selfToken.getReferenceNumber());
        Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, selfToken.getStatus());
        Assertions.assertEquals("Self-get E2E token", selfToken.getDescription());

        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
    }

    // Test weryfikujący, że token KSeF może pobrać listę tokenów zawierającą samego siebie bez uprawnienia CredentialsManage.
    // /tokens jest dozwolone dla właściciela tokenu i zwraca co najmniej jego własny token.
    // Kroki:
    // 1) Generuje token KSeF z uprawnieniami (InvoiceRead, InvoiceWrite) — bez CredentialsManage.
    // 2) Czeka aż status tokenu zmieni się na Active.
    // 3) Uwierzytelnia się do KSeF przy użyciu tego tokenu i pobiera jego własny access token.
    // 4) Pobiera listę tokenów używając access tokenu z kroku 3 (bez CredentialsManage).
    // 5) Weryfikuje, że lista zawiera własny token z poprawnymi danymi.
    @Test
    void sefToken_QuerySelf_WithoutCredentialsManage_ShouldReturnOwnToken() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("Self-query E2E token")
                .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));


        AuthOperationStatusResponse tokenAuthResult = authenticateWithKsefToken(tokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(tokenAuthResult);
        Assertions.assertNotNull(tokenAuthResult.getAccessToken().getToken());

        QueryTokensResponse queryResult = ksefClient.queryKsefTokens(List.of(), StringUtils.EMPTY, null, null, null, 10, tokenAuthResult.getAccessToken().getToken());
        Assertions.assertNotNull(queryResult);
        Assertions.assertNotNull(queryResult.getTokens());
        Assertions.assertTrue(queryResult.getTokens().stream().anyMatch(t -> t.getReferenceNumber().equals(tokenResponse.getReferenceNumber())));

        Optional<AuthenticationToken> ownTokenInListOptional = queryResult.getTokens().stream().filter(t -> t.getReferenceNumber().equals(tokenResponse.getReferenceNumber())).findFirst();
        Assertions.assertTrue(ownTokenInListOptional.isPresent());
        Assertions.assertEquals("Self-query E2E token", ownTokenInListOptional.get().getDescription());
        Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, ownTokenInListOptional.get().getStatus());

        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
    }

    // Test weryfikujący, że token KSeF wygenerowany z uprawnieniem Introspection
    // pozwala uwierzytelnionej nim sesji na odczyt statusu cudzej sesji interaktywnej.
    // Kroki:
    // 1) Otwiera własną sesję interaktywną przy użyciu głównego access tokenu (właściciela kontekstu).
    // 2) Generuje token KSeF z uprawnieniem Introspection i czeka na jego aktywację.
    // 3) Uwierzytelnia się tokenem KSeF, uzyskując access token z uprawnieniem Introspection.
    // 4) Sprawdza, że przy jego użyciu można odczytać status sesji z kroku 1 (wgląd, nie własność).
    @Test
    void ksefToken_WithIntrospectionPermission_CanReadForeignSessionStatus() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        EncryptionData encryptionData = cryptographyService.getEncryptionData();
        String ownSessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, authToken.accessToken());
        Assertions.assertNotNull(ownSessionReferenceNumber);

        KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                .withDescription("E2E Introspection token")
                .withPermissions(List.of(TokenPermissionType.INTROSPECTION))
                .build();
        GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
        Assertions.assertNotNull(tokenResponse.getReferenceNumber());
        Assertions.assertNotNull(tokenResponse.getToken());

        Awaitility.await().pollDelay(Duration.ZERO)
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));

        AuthOperationStatusResponse tokenAuthResult = authenticateWithKsefToken(tokenResponse.getToken(), contextNip);
        Assertions.assertNotNull(tokenAuthResult);
        Assertions.assertNotNull(tokenAuthResult.getAccessToken().getToken());

        SessionStatusResponse foreignSessionStatus = ksefClient.getSessionStatus(ownSessionReferenceNumber, tokenAuthResult.getAccessToken().getToken());
        Assertions.assertNotNull(foreignSessionStatus);

        ksefClient.revokeKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
    }

    // Test weryfikujący pobieranie i filtrowanie wygenerowanych tokenów KSeF:
    // generowanie, oczekiwanie na aktywację, wyszukanie wygenerowanych tokenów, unieważnienie oraz weryfikacja unieważnienia.
    // Kroki:
    // 1) Generuje 5 tokenów KSeF z uprawnieniami (InvoiceRead, InvoiceWrite).
    // 2) Czeka aż status tokenów zmieni się na Active.
    // 3) Wyszukuje wygenerowane tokeny:
    // 3b) Wyszukuje wybrany token
    // 4) Unieważnia wygenerowane tokeny.
    // 5) Sprawdza czy tokeny mają status Revoked.
    @Test
    void ksefTokensAsync_GenerateAndFilter_Positive() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair authToken = authWithCustomNip(contextNip, contextNip);

        // 1) Wygeneruj tokeny KSeF z uprawnieniami
        List<TokensResult> createdTokens = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            KsefTokenRequest createTokenRequest = new KsefTokenRequestBuilder()
                    .withDescription("E2E token - " + i)
                    .withPermissions(List.of(TokenPermissionType.INVOICE_READ, TokenPermissionType.INVOICE_WRITE))
                    .build();
            GenerateTokenResponse tokenResponse = ksefClient.generateKsefToken(createTokenRequest, authToken.accessToken());
            Assertions.assertNotNull(tokenResponse.getReferenceNumber());
            Assertions.assertNotNull(tokenResponse.getToken());

            // 2) Poczekaj, aż token stanie się aktywny
            Awaitility.await().pollDelay(Duration.ZERO)
                    .atMost(10, SECONDS)
                    .pollInterval(1, SECONDS)
                    .until(() -> isActiveToken(tokenResponse, authToken.accessToken()));

            AuthenticationToken activeToken = ksefClient.getKsefToken(tokenResponse.getReferenceNumber(), authToken.accessToken());
            Assertions.assertNotNull(activeToken);
            Assertions.assertEquals(AuthenticationTokenStatus.ACTIVE, activeToken.getStatus());

            createdTokens.add(new TokensResult(tokenResponse, activeToken));
        }

        // 3) Wyszukaj wygenerowane tokeny
        QueryTokensResponse queryAllResult = ksefClient.queryKsefTokens(List.of(), StringUtils.EMPTY, null, null, null, 10, authToken.accessToken());
        Assertions.assertNotNull(queryAllResult);
        Assertions.assertNotNull(queryAllResult.getTokens());
        Assertions.assertTrue(queryAllResult.getTokens().size() >= createdTokens.size());

        // 3b) Wyszukaj wybrany token
        QueryTokensResponse singleResult = ksefClient.queryKsefTokens(List.of(), "E2E token - 2", null, null, null, 10, authToken.accessToken());
        Assertions.assertNotNull(singleResult);
        Assertions.assertEquals(1, singleResult.getTokens().size());
        Assertions.assertEquals("E2E token - 2", singleResult.getTokens().getFirst().getDescription());

        // 4) Unieważnij token
        for (TokensResult r : createdTokens) {
            ksefClient.revokeKsefToken(r.tokenResponse().getReferenceNumber(), authToken.accessToken());

            // 5) Zweryfikuj, że token został unieważniony
            await().pollDelay(Duration.ZERO)
                    .atMost(10, SECONDS)
                    .pollInterval(1, SECONDS)
                    .until(() -> {
                        AuthenticationToken revokedToken = ksefClient.getKsefToken(r.tokenResponse().getReferenceNumber(), authToken.accessToken());
                        return revokedToken != null && revokedToken.getStatus() == AuthenticationTokenStatus.REVOKED;
                    });
        }
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

    private Boolean isAuthStatusReady(String referenceNumber, String tempToken) throws ApiException {
        AuthStatus authStatus = ksefClient.getAuthStatus(referenceNumber, tempToken);
        return authStatus != null && authStatus.getStatus().getCode() == 200;
    }

    private Boolean isActiveToken(GenerateTokenResponse token, String accessToken) throws ApiException {
        AuthenticationToken ksefToken = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
        return ksefToken != null && ksefToken.getStatus() == AuthenticationTokenStatus.ACTIVE;
    }

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
                .until(() -> isAuthStatusReady(response.getReferenceNumber(), response.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(response.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);

        return tokenResponse;
    }

    record TokensResult(GenerateTokenResponse tokenResponse, AuthenticationToken authenticationKsefToken) {
    }
}

package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.auth.AuthKsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.tokens.KsefTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
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
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class KsefTokenIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

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

        // step 2: wait for token to become ACTIVE
        Awaitility.await()
                .atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> {
                    AuthenticationToken ksefToken = ksefClient.getKsefToken(token.getReferenceNumber(), accessToken);
                    return ksefToken != null && ksefToken.getStatus() == AuthenticationTokenStatus.ACTIVE;
                });

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

        Awaitility.await()
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
                Arguments.of(EncryptionMethod.Rsa)
//                Arguments.of( EncryptionMethod.ECDsa) // [ECDSA is not supported yet]
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

        byte[] encryptedToken = switch (encryptionMethod) {
            case Rsa -> defaultCryptographyService
                    .encryptKsefTokenWithRSAUsingPublicKey(ksefToken.getToken(), challenge.getTimestamp());
            case ECDsa -> defaultCryptographyService
                    .encryptKsefTokenWithECDsaUsingPublicKey(ksefToken.getToken(), challenge.getTimestamp());
            default -> throw new IllegalArgumentException();
        };

        AuthKsefTokenRequest authTokenRequest = new AuthKsefTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextIdentifier(new ContextIdentifier(ContextIdentifier.IdentifierType.NIP , contextNip))
                .withEncryptedToken(Base64.getEncoder().encodeToString(encryptedToken))
                .build();

        SignatureResponse response = ksefClient.authenticateByKSeFToken(authTokenRequest);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isAuthStatusReady(response.getReferenceNumber(), response.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(response.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);
    }

    private Boolean isAuthStatusReady(String referenceNumber, String tempToken) throws ApiException {
        AuthStatus authStatus = ksefClient.getAuthStatus(referenceNumber, tempToken);
        return authStatus != null && authStatus.getStatus().getCode() == 200;
    }
}

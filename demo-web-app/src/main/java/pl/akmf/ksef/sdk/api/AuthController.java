package pl.akmf.ksef.sdk.api;

import jakarta.xml.bind.JAXBException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestSerializer;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateBuilders;
import pl.akmf.ksef.sdk.client.interfaces.CertificateService;
import pl.akmf.ksef.sdk.client.interfaces.SignatureService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenRefreshResponse;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.xml.AuthTokenRequest;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;
import pl.akmf.ksef.sdk.exception.StatusWaitingException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final DefaultKsefClient ksefClient;
    private final SignatureService signatureService;
    private final CertificateService certificateService;

    /**
     * Cały process autoryzacji krok po kroku
     * Zwraca token JWT oraz refreshToken
     * Inicjalizacja przyk�dowego identyfikatora - w tym przypadku NIP.
     *
     * @param context nip kontekstu w którym następuje próba uwierzytelnienia
     * @return AuthenticationOperationStatusResponse
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/auth-step-by-step/{context}")
    public AuthOperationStatusResponse authStepByStep(@PathVariable String context) throws ApiException, JAXBException, IOException {
        //wykonanie auth challenge
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        //xml niezbędny do uwierzytelnienia
        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextNip(context)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        //wygenerowanie certyfikatu oraz klucza prywatnego
        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-" + context, "Kowalski", "PL");

        SelfSignedCertificate cert = certificateService.generateSelfSignedCertificateRsa(x500);

        //podpisanie xml wygenerowanym certyfikatem oraz kluczem prywatnym
        String signedXml = signatureService.sign(xml.getBytes(), cert.certificate(), cert.getPrivateKey());

        // Przesłanie podpisanego XML do systemu KSeF
        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        isAuthStatusReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken());

        //pobranie tokenów
        return ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());
    }

    @GetMapping(value = "prepare-sample-cert-auth-request")
    public CertAuthRequest prepareSampleCertAuthRequest() throws CertificateEncodingException {
        CertificateBuilders.X500NameHolder x500 = new CertificateBuilders()
                .buildForOrganization("Kowalski sp. z o.o", "VATPL-1111111111", "Kowalski", "PL");
        SelfSignedCertificate selfSignedCertificate = certificateService.generateSelfSignedCertificateRsa(x500);
        String privateKeyBase64 = Base64.getEncoder().encodeToString(selfSignedCertificate.getPrivateKey().getEncoded());
        String certInBase64 = Base64.getEncoder().encodeToString(selfSignedCertificate.certificate().getEncoded());
        CertAuthRequest certAuthRequest = new CertAuthRequest();
        certAuthRequest.certInBase64 = certInBase64;
        certAuthRequest.privateKeyBase64 = privateKeyBase64;
        certAuthRequest.contextIdentifier = "1111111111";
        return certAuthRequest;
    }

    @PostMapping(value = "auth-with-ksef-certificate")
    public AuthOperationStatusResponse authWithKsefCert(@RequestBody CertAuthRequest request) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, ApiException, JAXBException, IOException {
        // 1. Wczytaj certyfikat X.509
        byte[] certBytes = Base64.getDecoder().decode(request.getCertInBase64());
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(certBytes));

        // 2. Wczytaj klucz prywatny (RSA)
        byte[] privateKeyBytes = Base64.getDecoder().decode(request.getPrivateKeyBase64());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 3. Pobierz challenge
        var challengeResponse = ksefClient.getAuthChallenge();
        String challenge = challengeResponse.getChallenge();


        var authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge)
                .withContextNip(request.getContextIdentifier())
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        // 5. Serializuj i podpisz
        var unsignedXml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);
        String signedXml = signatureService.sign(unsignedXml.getBytes(StandardCharsets.UTF_8), cert, privateKey);

        // 6. Wyślij żądanie uwierzytelnienia
        var submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        isAuthStatusReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken());

        //pobranie tokenów
        return ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());
    }

    /**
     * Proces odświeżania tokenu jwt
     * Zwraca nowy token JWT oraz refreshToken
     *
     * @param refreshToken token służący do odświeżania
     * @return AuthenticationTokenRefreshResponse
     * @throws ApiException if fails to make API call
     */
    @GetMapping(value = "/refreshToken/{refreshToken}")
    public AuthenticationTokenRefreshResponse refreshToken(@PathVariable String refreshToken) throws ApiException {
        return ksefClient.refreshAccessToken(refreshToken);
    }

    /**
     * Proces unieważniania tokenu jwt
     *
     * @return void
     * @throws ApiException if fails to make API call
     */
    @GetMapping(value = "/revoke")
    public void revokeToken(@RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.revokeAccessToken(authToken);

    }

    @Retryable(
            retryFor = {
                    StatusWaitingException.class,
            }, maxAttempts = 1,
            recover = "recoverAuthReadyStatusCheck",
            backoff = @Backoff(delay = 30)

    )
    private void isAuthStatusReady(String referenceNumber, String tempToken) throws ApiException {

        AuthStatus authStatus = ksefClient.getAuthStatus(referenceNumber, tempToken);

        if (authStatus.getStatus().getCode() != 200) {
            throw new StatusWaitingException("Authentication process has not been finished yet");
        }
    }

    @Recover
    public void recoverAuthReadyStatusCheck(String referenceNumber, String tempToken) throws ApiException {
        AuthStatus authStatus = ksefClient.getAuthStatus(referenceNumber, tempToken);

        if (authStatus.getStatus().getCode() != 200) {
            log.error("Timeout for authentication process");
            throw new StatusWaitingException("Authentication process has not been fineshed yet");
        }
    }
}

@Getter
class CertAuthRequest {
    String certInBase64;
    String contextIdentifier;
    String privateKeyBase64;
}

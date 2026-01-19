package pl.akmf.ksef.sdk.api;

import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.xml.AuthTokenRequest;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;
import pl.akmf.ksef.sdk.exception.StatusWaitingException;

import java.io.IOException;

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
     * Inicjalizacja przykladowego identyfikatora - w tym przypadku NIP.
     *
     * @param context nip kontekstu w którym następuje próba uwierzytelnienia
     * @return AuthenticationOperationStatusResponse
     * @throws ApiException if fails to make API call
     */
    @PostMapping(value = "/authenticationProcess/{context}")
    public AuthOperationStatusResponse authStepByStepAsOwner(@PathVariable String context) throws ApiException, JAXBException, IOException {
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
    private void recoverAuthReadyStatusCheck(String referenceNumber, String tempToken) throws ApiException {
        AuthStatus authStatus = ksefClient.getAuthStatus(referenceNumber, tempToken);

        if (authStatus.getStatus().getCode() != 200) {
            log.error("Timeout for authentication process");
            throw new StatusWaitingException("Authentication process has not been fineshed yet");
        }
    }
}

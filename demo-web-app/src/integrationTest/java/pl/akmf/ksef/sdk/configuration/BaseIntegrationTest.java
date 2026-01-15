package pl.akmf.ksef.sdk.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.akmf.ksef.sdk.TestClientApplication;
import pl.akmf.ksef.sdk.api.DefaultKsefClient;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestSerializer;
import pl.akmf.ksef.sdk.client.interfaces.CertificateService;
import pl.akmf.ksef.sdk.client.interfaces.QrCodeService;
import pl.akmf.ksef.sdk.client.interfaces.SignatureService;
import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.xml.AuthTokenRequest;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;
import pl.akmf.ksef.sdk.util.ExampleApiProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(
        classes = {TestClientApplication.class, IntegrationConfig.class},
        webEnvironment = RANDOM_PORT
)
@ContextConfiguration(classes = IntegrationConfig.class)
@EnableAutoConfiguration
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected WireMockServer wireMock;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ExampleApiProperties exampleApiProperties;

    @Autowired
    protected CertificateService certificateService;

    @Autowired
    protected QrCodeService qrCodeService;

    @Autowired
    protected SignatureService signatureService;

    @Autowired
    protected VerificationLinkService verificationLinkService;

    @Autowired
    protected DefaultKsefClient ksefClient;

    @BeforeEach
    public void prepare() {
        wireMock.start();
    }

    @AfterEach
    void clear() {
        wireMock.stop();
    }

    protected AuthTokensPair authWithCustomNip(String context, String subject) throws ApiException, JAXBException, IOException {
        return authWithCustomNip(context, subject, EncryptionMethod.Rsa);
    }

    protected AuthTokensPair authWithCustomPesel(String context, String subject) throws ApiException, JAXBException, IOException {
        return authWithCustomPesel(context, subject, EncryptionMethod.Rsa);
    }

    protected AuthTokensPair authWithCustomNip(String context, String subject, EncryptionMethod encryptionMethod) throws ApiException, JAXBException, IOException {
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextNip(context)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        SelfSignedCertificate cert = certificateService.getCompanySeal("Kowalski sp. z o.o", "VATPL-" + subject,
                "Kowalski", encryptionMethod);

        String signedXml = signatureService.sign(xml.getBytes(), cert.certificate(), cert.getPrivateKey());

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());

        return new AuthTokensPair(tokenResponse.getAccessToken().getToken(), tokenResponse.getRefreshToken().getToken());
    }

    protected AuthTokensPair authWithCustomNip(AuthTokenRequestBuilder authTokenRequestBuilder, SelfSignedCertificate cert) throws ApiException, JAXBException, IOException {
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = authTokenRequestBuilder
                .withChallenge(challenge.getChallenge())
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        String signedXml = signatureService.sign(xml.getBytes(), cert.certificate(), cert.getPrivateKey());

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());

        return new AuthTokensPair(tokenResponse.getAccessToken().getToken(), tokenResponse.getRefreshToken().getToken());
    }

    protected AuthTokensPair authWithCustomPesel(String context, String pesel, EncryptionMethod encryptionMethod) throws ApiException, JAXBException, IOException {
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextNip(context)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        SelfSignedCertificate cert = certificateService.getPersonalCertificate("M", "B", "PNOPL", pesel, "M B", encryptionMethod);

        String signedXml = signatureService.sign(xml.getBytes(), cert.certificate(), cert.getPrivateKey());

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());

        return new AuthTokensPair(tokenResponse.getAccessToken().getToken(), tokenResponse.getRefreshToken().getToken());
    }

    protected AuthTokensPair authAsPeppolProvider(String peppolId) throws ApiException, JAXBException,
            IOException {
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withPeppolId(peppolId)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        SelfSignedCertificate cert = certificateService.getCompanySeal("Kowalski sp. z o.o", peppolId, peppolId);

        String signedXml = signatureService.sign(xml.getBytes(), cert.certificate(), cert.getPrivateKey());

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());

        return new AuthTokensPair(tokenResponse.getAccessToken().getToken(), tokenResponse.getRefreshToken().getToken());
    }

    protected byte[] readBytesFromPath(String path) throws IOException {
        byte[] fileBytes;
        try (InputStream is = BaseIntegrationTest.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException();
            }
            fileBytes = is.readAllBytes();
        }
        return fileBytes;
    }

    private boolean isAuthProcessReady(String referenceNumber, String tempAuthToken) throws ApiException {
        AuthStatus checkAuthStatus = ksefClient.getAuthStatus(referenceNumber, tempAuthToken);
        return checkAuthStatus.getStatus().getCode() == 200;
    }

    public record AuthTokensPair(String accessToken, String refreshToken) {

    }
}

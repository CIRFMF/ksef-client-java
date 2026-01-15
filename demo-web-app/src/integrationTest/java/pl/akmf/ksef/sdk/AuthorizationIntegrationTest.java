package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestSerializer;
import pl.akmf.ksef.sdk.api.builders.certificate.SendCertificateEnrollmentRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.auth.AuthOperationStatusResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthStatus;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationChallengeResponse;
import pl.akmf.ksef.sdk.client.model.auth.AuthenticationTokenRefreshResponse;
import pl.akmf.ksef.sdk.client.model.auth.EncryptionMethod;
import pl.akmf.ksef.sdk.client.model.auth.SignatureResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateType;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.RetrieveCertificatesListItem;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;
import pl.akmf.ksef.sdk.client.model.xml.AuthTokenRequest;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class AuthorizationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void refreshTokenE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        // given
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        AuthTokensPair token = authWithCustomNip(contextNip, contextNip, EncryptionMethod.ECDsa);
        String initialAccessToken = token.accessToken();
        String initialRefreshToken = token.refreshToken();

        //when
        AuthenticationTokenRefreshResponse refreshTokenResult = ksefClient.refreshAccessToken(initialRefreshToken);

        //then
        Assertions.assertNotNull(refreshTokenResult);
        Assertions.assertNotEquals(initialAccessToken, refreshTokenResult.getAccessToken().getToken());
    }

    @Test
    void authorizeByKSeFCertificateWithECDSaEncoding() throws Exception {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //retrieve KSeF certificate && private key
        CertificateEnrollmentsInfoResponse enrollmentInfo = getEnrolmentInfo(accessToken);
        CsrResult csr = defaultCryptographyService.generateCsrWithEcdsa(enrollmentInfo);

        String referenceNumber = sendEnrollment(csr, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isEnrolmentStatusReady(referenceNumber, accessToken));

        CertificateEnrollmentStatusResponse enrolmentStatus = getEnrolmentStatus(referenceNumber, accessToken);

        List<RetrieveCertificatesListItem> certificateList = getCertificateList(enrolmentStatus.getCertificateSerialNumber(), accessToken);

        RetrieveCertificatesListItem certificate = certificateList.stream()
                .filter(c -> CertificateType.AUTHENTICATION.equals(c.getCertificateType()))
                .findFirst()
                .orElseThrow();

        X509Certificate x509Certificate = defaultCryptographyService.parseCertificateFromBytes(certificate.getCertificate());
        PrivateKey privateKey = defaultCryptographyService.parseEcdsaPrivateKeyFromPem(csr.privateKey());

        //authorize by KSeF certificate
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextNip(contextNip)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        String signedXml = signatureService.sign(xml.getBytes(), x509Certificate, privateKey);

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);
    }

    @Test
    void authorizeByKSeFCertificateWithRSaEncoding() throws Exception {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();

        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        //retrieve KSeF certificate && private key
        CertificateEnrollmentsInfoResponse enrollmentInfo = getEnrolmentInfo(accessToken);
        CsrResult csr = defaultCryptographyService.generateCsrWithRsa(enrollmentInfo);

        String referenceNumber = sendEnrollment(csr, accessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isEnrolmentStatusReady(referenceNumber, accessToken));

        CertificateEnrollmentStatusResponse enrolmentStatus = getEnrolmentStatus(referenceNumber, accessToken);

        List<RetrieveCertificatesListItem> certificateList = getCertificateList(enrolmentStatus.getCertificateSerialNumber(), accessToken);

        RetrieveCertificatesListItem certificate = certificateList.stream()
                .filter(c -> CertificateType.AUTHENTICATION.equals(c.getCertificateType()))
                .findFirst()
                .orElseThrow();

        X509Certificate x509Certificate = defaultCryptographyService.parseCertificateFromBytes(certificate.getCertificate());
        PrivateKey privateKey = defaultCryptographyService.parseRsaPrivateKeyFromPem(csr.privateKey());

        //authorize by KSeF certificate
        AuthenticationChallengeResponse challenge = ksefClient.getAuthChallenge();

        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge(challenge.getChallenge())
                .withContextNip(contextNip)
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .build();

        String xml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        String signedXml = signatureService.sign(xml.getBytes(), x509Certificate, privateKey);

        SignatureResponse submitAuthTokenResponse = ksefClient.submitAuthTokenRequest(signedXml, false);

        //Czekanie na zakończenie procesu
        await().atMost(14, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isAuthProcessReady(submitAuthTokenResponse.getReferenceNumber(), submitAuthTokenResponse.getAuthenticationToken().getToken()));

        AuthOperationStatusResponse tokenResponse = ksefClient.redeemToken(submitAuthTokenResponse.getAuthenticationToken().getToken());
        Assertions.assertNotNull(tokenResponse);
    }

    private boolean isAuthProcessReady(String referenceNumber, String tempAuthToken) throws ApiException {
        AuthStatus checkAuthStatus = ksefClient.getAuthStatus(referenceNumber, tempAuthToken);
        return checkAuthStatus.getStatus().getCode() == 200;
    }

    private CertificateEnrollmentsInfoResponse getEnrolmentInfo(String accessToken) throws ApiException {
        CertificateEnrollmentsInfoResponse response = ksefClient.getCertificateEnrollmentInfo(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getOrganizationIdentifier());

        return response;
    }

    private String sendEnrollment(CsrResult csr, String accessToken) throws ApiException {
        SendCertificateEnrollmentRequest request = new SendCertificateEnrollmentRequestBuilder()
                .withValidFrom(OffsetDateTime.now().minusMinutes(1).toString())
                .withCsr(csr.csr())
                .withCertificateName("certificate")
                .withCertificateType(CertificateType.AUTHENTICATION)
                .build();

        CertificateEnrollmentResponse response = ksefClient.sendCertificateEnrollment(request, accessToken);

        return response.getReferenceNumber();
    }

    private Boolean isEnrolmentStatusReady(String referenceNumber, String accessToken) {
        try {
            CertificateEnrollmentStatusResponse response =
                    ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);
            return response != null &&
                    response.getStatus().getCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private CertificateEnrollmentStatusResponse getEnrolmentStatus(String referenceNumber, String accessToken) throws ApiException {
        return ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);
    }

    private List<RetrieveCertificatesListItem> getCertificateList(String certificateSerialNumber, String accessToken) throws ApiException {
        return ksefClient.getCertificateList(new CertificateListRequest(List.of(certificateSerialNumber)), accessToken).getCertificates();
    }
}

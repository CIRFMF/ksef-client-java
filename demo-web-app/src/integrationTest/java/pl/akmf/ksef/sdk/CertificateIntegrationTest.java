package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateMetadataListRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.certificate.CertificateRevokeRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.certificate.SendCertificateEnrollmentRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateLimitsResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateRevocationReason;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateRevokeRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateType;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;
import pl.akmf.ksef.sdk.client.model.certificate.QueryCertificatesRequest;
import pl.akmf.ksef.sdk.client.model.certificate.SendCertificateEnrollmentRequest;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class CertificateIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    @Test
    void certificateE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String delegateNip = IdentifierGeneratorUtils.generateRandomNIP();

        //login as owner
        String accessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        //grant credential to delegate
        String grantReferenceNumber = grantPermission(delegateNip, accessToken);

        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantReferenceNumber, accessToken));

        //login as delegate in owner context
        String delegateAccessToken = authWithCustomNip(ownerNip, delegateNip).accessToken();

        //check certificate limit
        getCertificateLimitAsync(delegateAccessToken);

        //get enrollment info
        CertificateEnrollmentsInfoResponse enrollmentInfo = getEnrolmentInfo(delegateAccessToken);

        //generate certificate
        String referenceNumber = sendEnrollment(enrollmentInfo, delegateAccessToken);

        await().atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> isEnrolmentStatusReady(referenceNumber, delegateAccessToken));

        CertificateEnrollmentStatusResponse enrolmentStatus = getEnrolmentStatus(referenceNumber, delegateAccessToken);

        //retrieve certificate
        getCertificateList(enrolmentStatus.getCertificateSerialNumber(), delegateAccessToken);

        //revoke certificate
        revokeCertificate(enrolmentStatus.getCertificateSerialNumber(), delegateAccessToken);

        //get certificate
        getMedataCertificateList(enrolmentStatus.getCertificateSerialNumber(), delegateAccessToken);
    }

    private Boolean isEnrolmentStatusReady(String referenceNumber, String accessToken) {
        try {
            CertificateEnrollmentStatusResponse response = ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);
            return response != null &&
                    response.getStatus().getCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private String grantPermission(String nipValue, String accessToken) throws ApiException {
        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, nipValue))
                .withPermissions(List.of(PersonPermissionType.CREDENTIALSMANAGE))
                .withDescription("e2e test")
                .build();

        OperationResponse response = ksefClient.grantsPermissionPerson(request, accessToken);
        Assertions.assertNotNull(response);
        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private void getMedataCertificateList(String certificateSerialNumber, String accessToken) throws ApiException {
        QueryCertificatesRequest request = new CertificateMetadataListRequestBuilder()
                .build();

        CertificateMetadataListResponse response = ksefClient.getCertificateMetadataList(request, 10, 0, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCertificates().getFirst().getCertificateSerialNumber(), certificateSerialNumber);
    }

    private void revokeCertificate(String serialNumber, String accessToken) throws ApiException {
        CertificateRevokeRequest request = new CertificateRevokeRequestBuilder()
                .withRevocationReason(CertificateRevocationReason.KEYCOMPROMISE)
                .build();

        ksefClient.revokeCertificate(request, serialNumber, accessToken);
    }

    private void getCertificateList(String certificateSerialNumber, String accessToken) throws ApiException {
        CertificateListResponse certificateResponse =
                ksefClient.getCertificateList(new CertificateListRequest(List.of(certificateSerialNumber)), accessToken);

        Assertions.assertNotNull(certificateResponse);
        Assertions.assertEquals(1, certificateResponse.getCertificates().size());
    }

    private CertificateEnrollmentStatusResponse getEnrolmentStatus(String referenceNumber, String accessToken) throws ApiException {
        CertificateEnrollmentStatusResponse response = ksefClient.getCertificateEnrollmentStatus(referenceNumber, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus().getCode());
        return response;
    }

    private String sendEnrollment(CertificateEnrollmentsInfoResponse enrollmentInfo, String accessToken) throws ApiException {
        CsrResult csr = defaultCryptographyService.generateCsrWithRsa(enrollmentInfo);

        SendCertificateEnrollmentRequest request = new SendCertificateEnrollmentRequestBuilder()
                .withValidFrom(OffsetDateTime.now().toString())
                .withCsr(csr.csr())
                .withCertificateName("certificate")
                .withCertificateType(CertificateType.AUTHENTICATION)
                .build();

        CertificateEnrollmentResponse response = ksefClient.sendCertificateEnrollment(request, accessToken);
        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }

    private CertificateEnrollmentsInfoResponse getEnrolmentInfo(String accessToken) throws ApiException {
        CertificateEnrollmentsInfoResponse response = ksefClient.getCertificateEnrollmentInfo(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getOrganizationIdentifier());

        return response;
    }

    private void getCertificateLimitAsync(String accessToken) throws ApiException {
        CertificateLimitsResponse response = ksefClient.getCertificateLimits(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getCanRequest());
    }
}

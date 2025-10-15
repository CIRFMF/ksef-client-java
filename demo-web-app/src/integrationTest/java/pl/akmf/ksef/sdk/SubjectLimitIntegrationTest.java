package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.limit.CertificateLimit;
import pl.akmf.ksef.sdk.client.model.limit.ChangeSubjectCertificateLimitRequest;
import pl.akmf.ksef.sdk.client.model.limit.EnrollmentLimit;
import pl.akmf.ksef.sdk.client.model.limit.GetSubjectLimitResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;

class SubjectLimitIntegrationTest extends BaseIntegrationTest {

    @Test
    void subjectLimitE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        changeSubjectLimit(accessToken);

        GetSubjectLimitResponse limitAfterChanges = getExpectedResponseAfterChanges();

        getSubjectLimit(accessToken, limitAfterChanges);

        resetSubjectLimit(accessToken);

        GetSubjectLimitResponse baseLimits = getExpectedBaseResponse();

        getSubjectLimit(accessToken, baseLimits);
    }

    private void getSubjectLimit(String accessToken, GetSubjectLimitResponse expectedResponse) throws ApiException {
        GetSubjectLimitResponse response = ksefClient.getSubjectCertificateLimit(accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse.getCertificate().getMaxCertificates(), response.getCertificate().getMaxCertificates());
        Assertions.assertEquals(expectedResponse.getEnrollment().getMaxEnrollments(), response.getEnrollment().getMaxEnrollments());
    }

    private void changeSubjectLimit(String accessToken) throws ApiException {
        ChangeSubjectCertificateLimitRequest request = new ChangeSubjectCertificateLimitRequest();
        request.setCertificate(new CertificateLimit(15));
        request.setEnrollment(new EnrollmentLimit(15));
        request.setSubjectIdentifierType(ChangeSubjectCertificateLimitRequest.SubjectType.NIP);

        ksefClient.changeSubjectLimitTest(request, accessToken);
    }

    private void resetSubjectLimit(String accessToken) throws ApiException {
        ksefClient.resetSubjectCertificateLimit(accessToken);
    }

    private GetSubjectLimitResponse getExpectedBaseResponse() {
        GetSubjectLimitResponse response = new GetSubjectLimitResponse();

        response.setCertificate(new CertificateLimit(15));
        response.setEnrollment(new EnrollmentLimit(15));

        return response;
    }

    private GetSubjectLimitResponse getExpectedResponseAfterChanges() {
        GetSubjectLimitResponse response = new GetSubjectLimitResponse();

        response.setCertificate(new CertificateLimit(15));
        response.setEnrollment(new EnrollmentLimit(15));

        return response;
    }
}

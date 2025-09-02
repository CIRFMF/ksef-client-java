package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.builders.certificate.SendCertificateEnrollmentRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.api.services.DefaultKsefClient;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentStatusResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateEnrollmentsInfoResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateLimitsResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.QueryCertificatesRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateMetadataListResponse;
import pl.akmf.ksef.sdk.client.model.certificate.CertificateRevokeRequest;
import pl.akmf.ksef.sdk.client.model.certificate.CsrResult;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class CertificateController {
    private final DefaultKsefClient ksefClient;

    @GetMapping(value = "/limits")
    public CertificateLimitsResponse getCertificateLimit(@RequestHeader(name = "Authorization") String authToken) throws ApiException {
        return ksefClient.getCertificateLimits(authToken);
    }

    @GetMapping(value = "/enrollment-info")
    public CertificateEnrollmentsInfoResponse getCertificateEnrollmentInfo(@RequestHeader(name = "Authorization") String authToken) throws ApiException {
        return ksefClient.getCertificateEnrollmentInfo(authToken);
    }

    @PostMapping(value = "/send-enrollment")
    public CertificateEnrollmentResponse certificateEnrollment(@RequestBody CertificateEnrollmentsInfoResponse certificateEnrollmentsInfoResponse,
                                                               @RequestHeader(name = "Authorization") String authToken) throws Exception {
        DefaultCryptographyService defaultCryptographyService = new DefaultCryptographyService(ksefClient);

        //wygenerowanie CSR na podstawie otrzymanych wcześniej informacji
        CsrResult csr = defaultCryptographyService.generateCsr(certificateEnrollmentsInfoResponse);

        //stworzenie requesta
        var request = new SendCertificateEnrollmentRequestBuilder()
                .withCertificateName("certificateName")
                .withCsr(csr.csr())
                .withValidFrom(OffsetDateTime.now().toString())
                .build();

        //rozpoczęcie procesu generowania certyfikatu
        return ksefClient.sendCertificateEnrollment(request, authToken);
    }

    @GetMapping(value = "/enrollment-status/{referenceNumber}")
    public CertificateEnrollmentStatusResponse getCertificateEnrollmentStatus(@PathVariable String referenceNumber,
                                                                              @RequestHeader(name = "Authorization") String authToken) throws ApiException {
        return ksefClient.getCertificateEnrollmentStatus(referenceNumber, authToken);
    }

    @PostMapping(value = "/certListMetadata")
    public CertificateMetadataListResponse getMetadataCertificateList(@RequestBody QueryCertificatesRequest queryCertificatesRequest,
                                                                      @RequestHeader(name = "Authorization") String authToken) throws ApiException {
        return ksefClient.getCertificateMetadataList(queryCertificatesRequest, 10, 0, authToken);
    }

    @PostMapping(value = "/retrieve")
    public CertificateListResponse getCertificateList(@RequestBody CertificateListRequest request,
                                                      @RequestHeader(name = "Authorization") String authToken) throws ApiException {
        return ksefClient.getCertificateList(request, authToken);
    }

    @PostMapping(value = "/revoke/{serialNumber}")
    public void revokeCertificate(@PathVariable String serialNumber, @RequestBody CertificateRevokeRequest request,
                                  @RequestHeader(name = "Authorization") String authToken) throws ApiException {
        ksefClient.revokeCertificate(request, serialNumber, authToken);
    }
}

package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.euentity.EuEntityPermissionsQueryRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.euentity.GrantEUEntityPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.euentityrepresentative.GrantEUEntityRepresentativePermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.certificate.SelfSignedCertificate;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.euentity.ContextIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionsGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.GrantEUEntityRepresentativePermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermission;
import pl.akmf.ksef.sdk.client.model.permission.search.EuEntityPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryEuEntityPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class EuEntityRepresentativePermissionIntegrationTest extends BaseIntegrationTest {

    /*
     * 1. Autentykacja jako owner - context NIP
     * 2. Owner nadaje uprawnienia administracyjne jednostce organizacyjnej - context NipVatEu
     * 3. Pobranie uprawnień nadanych contextowi
     * 4. Autentykacja jako jednostka organizacyjna - context NipVatEu
     * 5. W tokenie powinny być role jednostki
     * 6. Nadanie uprawnień reprezentanta
     * 7. Pobranie/sprawdzenie nadanych uprawnień
     * 8. Odwołanie uprawnień (wymaga odczekania kilku sekund...)
     * 9. Sprawdzenie uprawnień po odwołaniu
     *
     */
    @Test
    void grantAdministrativePermission_E2E_ReturnsExpectedResults() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.getRandomNip();
        String ownerVatEu = IdentifierGeneratorUtils.getRandomVatEU("ES");
        String ownerNipVatEu = IdentifierGeneratorUtils.getNipVatEU(ownerNip, ownerVatEu);

        String euEntityNip = IdentifierGeneratorUtils.getRandomNip();
        String euEntityVatEu = IdentifierGeneratorUtils.getRandomVatEU("ES");
        String euEntityNipVatEu = IdentifierGeneratorUtils.getNipVatEU(euEntityNip, euEntityVatEu);

        String euRepresentativeEntityNip = IdentifierGeneratorUtils.getRandomNip();
        String euRepresentativeEntityVatEu = IdentifierGeneratorUtils.getRandomVatEU("ES");
        String euRepresentativeEntityNipVatEu = IdentifierGeneratorUtils.getNipVatEU(euRepresentativeEntityNip, euRepresentativeEntityVatEu);

        SelfSignedCertificate ownerCertificate = certificateService.getPersonalCertificate(
                "M", "B", "TINPL", ownerNip,
                "M B");
        String ownerCertificateFingerprint = certificateService.getSha256Fingerprint(ownerCertificate.certificate());

        SelfSignedCertificate euEntitySealCertificate = certificateService.getCompanySeal(
                "My Company", euEntityNipVatEu, "Common company");
        String euEntitySealCertificateFingerprint = certificateService.getSha256Fingerprint(euEntitySealCertificate.certificate());

        SelfSignedCertificate euEntityPersonalCertificate = certificateService.getPersonalCertificate(
                "MM", "BB", "TINPL", euEntityNip,
                "abcd");
        String euEntityPersonalCertificateFingerprint = certificateService.getSha256Fingerprint(euEntityPersonalCertificate.certificate());

        SelfSignedCertificate euRepresentativeEntityCertificate = certificateService.getPersonalCertificate(
                "Reprezentant M", "Reprezentant B", "TINPL", euRepresentativeEntityNip,
                "commonName");
        String euRepresentativeEntityCertificateFingerprint = certificateService.getSha256Fingerprint(euRepresentativeEntityCertificate.certificate());

        AuthTokensPair ownerAuthInfo = authWithCustomNip(
                new AuthTokenRequestBuilder()
                        .withContextNip(ownerNip)
                        .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT),
                ownerCertificate);
        String grantEuEntityPermissionOperationReferenceNumber = grantEuEntityPermission(ownerNipVatEu, euEntityPersonalCertificateFingerprint, ownerAuthInfo.accessToken());
        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantEuEntityPermissionOperationReferenceNumber, ownerAuthInfo.accessToken()));
        searchPermissionOperationIds(euEntityPersonalCertificateFingerprint, 4, ownerAuthInfo.accessToken());

        AuthTokensPair euAuthInfo = authWithCustomNip(
                new AuthTokenRequestBuilder()
                        .withNipVatEu(ownerNipVatEu)// nipvateu kontekstu
                        .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_FINGERPRINT), // typ identyfiktora jednostki eu
                euEntityPersonalCertificate // certyfikat jednostki eu
        );
        String grantEuEntityRepresentativePermissionOperationReferenceNumber = grantEuEntityRepresentativePermission(euRepresentativeEntityCertificateFingerprint, euAuthInfo.accessToken());
        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantEuEntityRepresentativePermissionOperationReferenceNumber, euAuthInfo.accessToken()));
        // pobierz listę uprawnień reprezentanta
        List<String> grantedRepresentativePermission = searchPermissionOperationIds(euRepresentativeEntityCertificateFingerprint,
                2, euAuthInfo.accessToken());

        grantedRepresentativePermission.forEach(e -> {
            String revokeReferenceNumber = revokePermission(e, euAuthInfo.accessToken());

            await().atMost(30, SECONDS)
                    .pollInterval(2, SECONDS)
                    .until(() -> isOperationFinish(revokeReferenceNumber, euAuthInfo.accessToken()));
        });

        searchPermissionOperationIds(euRepresentativeEntityCertificateFingerprint, 0, euAuthInfo.accessToken());
    }

    private String revokePermission(String operationId, String accessToken) {
        try {
            return ksefClient.revokeCommonPermission(operationId, accessToken).getReferenceNumber();
        } catch (ApiException e) {
            Assertions.fail(e.getMessage());
        }
        return null;
    }

    private List<String> searchPermissionOperationIds(String authorizedFingerprintIdentifier, int expectedNumberOfPermissions, String accessToken) throws ApiException {
        EuEntityPermissionsQueryRequest request = new EuEntityPermissionsQueryRequestBuilder()
                .withAuthorizedFingerprintIdentifier(authorizedFingerprintIdentifier)
                .build();

        QueryEuEntityPermissionsResponse response = ksefClient.searchGrantedEuEntityPermissions(request, 0, 10, accessToken);

        Assertions.assertEquals(expectedNumberOfPermissions, response.getPermissions().size());

        return response.getPermissions()
                .stream()
                .map(EuEntityPermission::getId)
                .toList();
    }

    private String grantEuEntityRepresentativePermission(String fingerprint, String accessToken) throws ApiException {
        GrantEUEntityRepresentativePermissionsRequest request = new GrantEUEntityRepresentativePermissionsRequestBuilder()
                .withSubjectIdentifier(new SubjectIdentifier(SubjectIdentifier.IdentifierType.FINGERPRINT, fingerprint))
                .withPermissions(List.of(EuEntityPermissionType.INVOICE_WRITE, EuEntityPermissionType.INVOICE_READ))
                .withDescription("Representative for EU Entity")
                .build();

        OperationResponse response = ksefClient.grantsPermissionEUEntityRepresentative(request, accessToken);

        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }

    private String grantEuEntityPermission(String ownerNipVatEu, String euEntityPersonalCertificateFingerprint, String accessToken) throws ApiException {
        EuEntityPermissionsGrantRequest request = new GrantEUEntityPermissionsRequestBuilder()
                // subject to jednostka unijna - jej nadajemy uprawnienia
                // typ identyfikatora dla subject (czyli jednostki eu, ktorej nadajemy uprawnienia): fingerprint
                // wartość identyfikatora dla subject: fingerprint certyfikatu jednostki eu w sha256
                .withSubject(new SubjectIdentifier(SubjectIdentifier.IdentifierType.FINGERPRINT, euEntityPersonalCertificateFingerprint))
                .withEuEntityName("MB Company")
                // context to moja firma - bo to ja nadaje uprawnienia, czyli wykonuje akcje w moim kontekscie
                .withContext(new ContextIdentifier(ContextIdentifier.IdentifierType.NIP_VAT_UE, ownerNipVatEu))
                .withDescription("EU Company")
                .build();

        OperationResponse response = ksefClient.grantsPermissionEUEntity(request, accessToken);

        Assertions.assertNotNull(response);

        return response.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }
}

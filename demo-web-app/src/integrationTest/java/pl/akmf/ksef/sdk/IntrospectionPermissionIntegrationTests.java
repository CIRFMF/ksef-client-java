package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.OpenOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.session.SendInvoiceOnlineSessionRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntityPersonByIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntitySubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.indirect.PermissionsIndirectEntitySubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonById;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.FormCode;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.OpenOnlineSessionResponse;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceOnlineSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.online.SendInvoiceResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntrospectionPermissionIntegrationTests extends BaseIntegrationTest {

    private static final int successfulInvoiceCountExpected = 1;

    // Osoba posiadająca uprawnienie Introspection ma dostęp do historii/statusu cudzej sesji (tylko wgląd),
    // ale nie może zamknąć sesji, której nie jest właścicielem.
    @Test
    void personWithIntrospection_CanViewForeignSessionStatus_ButCannotCloseIt() throws JAXBException, IOException, ApiException {
        String ownerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String employeeNip = IdentifierGeneratorUtils.generateRandomNIP();
        String thirdPersonNip = IdentifierGeneratorUtils.generateRandomNIP();

        // Właściciel uwierzytelnia się i nadaje pracownikowi InvoiceWrite oraz InvoiceRead
        String ownerAccessToken = authWithCustomNip(ownerNip, ownerNip).accessToken();

        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, employeeNip))
                .withPermissions(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .withDescription("E2E grant InvoiceWrite+InvoiceRead to employee")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();

        OperationResponse grantToEmployee = ksefClient.grantsPermissionPerson(request, ownerAccessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantToEmployee.getReferenceNumber(), ownerAccessToken));

        // Pracownik uwierzytelnia się w kontekście właściciela i wysyła fakturę w sesji interaktywnej (sesja pozostaje otwarta)
        String employeeAccessToken = authWithCustomNip(ownerNip, employeeNip).accessToken();

        EncryptionData encryptionData = cryptographyService.getEncryptionData();

        String sessionReferenceNumber = openOnlineSession(encryptionData, SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA, employeeAccessToken);
        Assertions.assertNotNull(sessionReferenceNumber);

        sendInvoiceOnlineSession(ownerNip, sessionReferenceNumber, encryptionData,
                "/xml/invoices/sample/invoice-template_v3.xml", employeeAccessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(5, SECONDS)
                .until(() -> isInvoicesInSessionProcessed(sessionReferenceNumber, employeeAccessToken));

        SessionStatusResponse statusAfterSend = ksefClient.getSessionStatus(sessionReferenceNumber, employeeAccessToken);

        Assertions.assertNotNull(statusAfterSend);
        Assertions.assertEquals(successfulInvoiceCountExpected, statusAfterSend.getSuccessfulInvoiceCount());
        Assertions.assertNull(statusAfterSend.getFailedInvoiceCount());
        Assertions.assertEquals(100, statusAfterSend.getStatus().getCode());

        // Właściciel nadaje trzeciej osobie na razie tylko InvoiceWrite oraz InvoiceRead (bez Introspection)
        PermissionsIndirectEntitySubjectDetails subjectDetails = new PermissionsIndirectEntitySubjectDetails();
        subjectDetails.setSubjectDetailsType(PermissionsIndirectEntitySubjectDetailsType.PersonByIdentifier);
        subjectDetails.setPersonById(new PermissionsIndirectEntityPersonByIdentifier("Test", "Ttest"));
        GrantPersonPermissionsRequest grantsPermissionPerson = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, thirdPersonNip))
                .withPermissions(List.of(PersonPermissionType.INVOICEWRITE, PersonPermissionType.INVOICEREAD))
                .withDescription("E2E grant InvoiceWrite+InvoiceRead to third person")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        OperationResponse grantToThirdPerson = ksefClient.grantsPermissionPerson(grantsPermissionPerson, ownerAccessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantToThirdPerson.getReferenceNumber(), ownerAccessToken));

        // Trzecia osoba uwierzytelnia się w kontekście właściciela
        String thirdPersonAccessToken = authWithCustomNip(ownerNip, thirdPersonNip).accessToken();

        // Trzecia osoba działa jako pracownik uwierzytelniony w kontekście właściciela, ale bez Introspection
        // ma dostęp jedynie do sesji, które sama utworzyła w tym kontekście - nie do wszystkich sesji podmiotu.
        // Sesja utworzona przez innego pracownika (employeeNip) nie jest więc dla niej dostępna, dlatego odczyt jej statusu zwraca błąd.
        String finalThirdPersonAccessToken1 = thirdPersonAccessToken;
        assertThrows(ApiException.class, () ->
                ksefClient.getSessionStatus(sessionReferenceNumber, finalThirdPersonAccessToken1));

        // Właściciel dodatkowo nadaje trzeciej osobie (pracownikowi działającemu w jego kontekście) uprawnienie Introspection -
        // sam właściciel jako podmiot kontekstu zawsze widzi historię wszystkich sesji w swoim kontekście,
        // a Introspection rozszerza ten wgląd również na pracownika, któremu się je nada.
        PermissionsIndirectEntitySubjectDetails subjectDetailsIntrospection = new PermissionsIndirectEntitySubjectDetails();
        subjectDetailsIntrospection.setSubjectDetailsType(PermissionsIndirectEntitySubjectDetailsType.PersonByIdentifier);
        subjectDetailsIntrospection.setPersonById(new PermissionsIndirectEntityPersonByIdentifier("Test", "Ttest"));
        GrantPersonPermissionsRequest grantsPermissionPersonIntrospection = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.NIP, thirdPersonNip))
                .withPermissions(List.of(PersonPermissionType.INTROSPECTION))
                .withDescription("E2E grant Introspection to third person")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        OperationResponse grantIntrospectionToThirdPerson = ksefClient.grantsPermissionPerson(grantsPermissionPersonIntrospection, ownerAccessToken);

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isOperationFinish(grantIntrospectionToThirdPerson.getReferenceNumber(), ownerAccessToken));

        // Nowe uprawnienie wymaga ponownego uwierzytelnienia, żeby zostało uwzględnione w tokenie
        thirdPersonAccessToken = authWithCustomNip(ownerNip, thirdPersonNip).accessToken();

        // Dzięki Introspection można teraz zobaczyć status cudzej sesji
        SessionStatusResponse statusVisibleToThirdPerson = ksefClient.getSessionStatus(sessionReferenceNumber, thirdPersonAccessToken);
        Assertions.assertNotNull(statusVisibleToThirdPerson);

        // Nie może zamknąć cudzej sesji (Introspection daje tylko wgląd, nie jest właścicielem sesji)
        String finalThirdPersonAccessToken = thirdPersonAccessToken;
        assertThrows(ApiException.class, () ->
                ksefClient.closeOnlineSession(sessionReferenceNumber, finalThirdPersonAccessToken));
    }

    private String sendInvoiceOnlineSession(String sellerNip, String sessionReferenceNumber, EncryptionData encryptionData,
                                            String path, String accessToken) throws IOException, ApiException {
        String invoiceTemplate = new String(readBytesFromPath(path), StandardCharsets.UTF_8)
                .replace("#nip#", sellerNip)
                .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("#invoice_number#", UUID.randomUUID().toString());

        byte[] invoice = invoiceTemplate.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedInvoice = cryptographyService.encryptBytesWithAES256(invoice,
                encryptionData.cipherKey(),
                encryptionData.cipherIv());

        FileMetadata invoiceMetadata = cryptographyService.getMetaData(invoice);
        FileMetadata encryptedInvoiceMetadata = cryptographyService.getMetaData(encryptedInvoice);

        SendInvoiceOnlineSessionRequest sendInvoiceOnlineSessionRequest = new SendInvoiceOnlineSessionRequestBuilder()
                .withInvoiceHash(invoiceMetadata.getHashSHA())
                .withInvoiceSize(invoiceMetadata.getFileSize())
                .withEncryptedInvoiceHash(encryptedInvoiceMetadata.getHashSHA())
                .withEncryptedInvoiceSize(encryptedInvoiceMetadata.getFileSize())
                .withEncryptedInvoiceContent(Base64.getEncoder().encodeToString(encryptedInvoice))
                .build();

        SendInvoiceResponse sendInvoiceResponse = ksefClient.onlineSessionSendInvoice(sessionReferenceNumber, sendInvoiceOnlineSessionRequest, accessToken);
        Assertions.assertNotNull(sendInvoiceResponse);
        Assertions.assertNotNull(sendInvoiceResponse.getReferenceNumber());

        return sendInvoiceResponse.getReferenceNumber();
    }

    private Boolean isOperationFinish(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
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

    private boolean isInvoicesInSessionProcessed(String sessionReferenceNumber, String accessToken) {
        try {
            SessionStatusResponse statusResponse = ksefClient.getSessionStatus(sessionReferenceNumber, accessToken);
            return statusResponse != null &&
                    statusResponse.getSuccessfulInvoiceCount() != null &&
                    statusResponse.getSuccessfulInvoiceCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

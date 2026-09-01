package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.permission.person.GrantPersonPermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoice;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoicesQueryRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifierInvoicesQueryResponseItem;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersByKsefNumberQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersQueryRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersQueryResponse;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.CollectiveIdentifiersQueryResponseItem;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.GenerateCollectiveIdentifierRequest;
import pl.akmf.ksef.sdk.client.model.collectiveidentifiers.GenerateCollectiveIdentifierResponse;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionPersonById;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionsSubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.CompressionType;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.FilesUtil;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

class CollectiveIdentifiersIntegrationTests extends BaseIntegrationTest {

    private static final int NUMBER_OF_PARTS = 2;
    private static final int FIRST_GROUP_INVOICES_COUNT = 7;
    private static final int SECOND_GROUP_INVOICES_COUNT = 5;
    private static final String invoiceTemplate = "/xml/invoices/sample/invoice-template-fa-3-with-custom-subject_2.xml";

    // Weryfikuje pełny cykl generowania dwóch odrębnych identyfikatorów zbiorczych dla rozłącznych grup faktur sprzedawcy
    // Kroki:
    // 1) Sprzedawca wystawia kilka faktur i czeka na ich dostępność w systemie
    // 2) Wystawione faktury dzielone są na dwie rozłączne grupy, dla każdej generowany jest osobny identyfikator zbiorczy
    // 3) Weryfikacja odnalezienia obu identyfikatorów po numerze KSeF faktury z odpowiedniej grupy
    // 4) Jednym żądaniem (`getCollectiveIdentifierInvoices` z listą obu numerów) weryfikacja odnalezienia
    //    wszystkich faktur z obu grup, każda poprawnie przypisana do właściwego identyfikatora zbiorczego
    // 5) Weryfikacja odnalezienia obu identyfikatorów w wynikach zapytania listującego
    @Test
    void generateCollectiveIdentifier_ThenFindItByKsefNumberAndByNumber() throws JAXBException, IOException, ApiException {
        String sellerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String sellerToken = authWithCustomNip(sellerNip, sellerNip).accessToken();

        String buyerNip = IdentifierGeneratorUtils.generateRandomNIP();

        // Dwie rozłączne grupy faktur - każda trafia do osobnego identyfikatora zbiorczego.
        List<String> firstGroupKsefNumbers = sendInvoicesAndGetKsefNumbers(sellerNip, buyerNip, FIRST_GROUP_INVOICES_COUNT);
        List<String> secondGroupKsefNumbers = sendInvoicesAndGetKsefNumbers(sellerNip, buyerNip, SECOND_GROUP_INVOICES_COUNT);
        List<String> ksefNumbers = new ArrayList<>();
        ksefNumbers.addAll(firstGroupKsefNumbers);
        ksefNumbers.addAll(secondGroupKsefNumbers);

        OffsetDateTime dateFrom = OffsetDateTime.now().minusMinutes(50);

        String firstCollectiveIdentifierNumber = generateCollectiveIdentifierNumber(firstGroupKsefNumbers, sellerToken);
        String secondCollectiveIdentifierNumber = generateCollectiveIdentifierNumber(secondGroupKsefNumbers, sellerToken);

        await().pollDelay(Duration.ZERO)
                .atMost(60, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    CollectiveIdentifiersByKsefNumberQueryResponse byKsefNumber1 = ksefClient.getCollectiveIdentifiersByKsefNumber(firstGroupKsefNumbers.get(0),
                            sellerToken, null, 20);
                    CollectiveIdentifiersByKsefNumberQueryResponse byKsefNumber2 = ksefClient.getCollectiveIdentifiersByKsefNumber(secondGroupKsefNumbers.get(0),
                            sellerToken, null, 20);

                    Assertions.assertNotNull(byKsefNumber1);
                    Assertions.assertNotNull(byKsefNumber2);
                    return byKsefNumber1.getCollectiveIdentifiers().stream()
                            .anyMatch(r -> r.getCollectiveIdentifierNumber().equals(firstCollectiveIdentifierNumber))
                            && byKsefNumber2.getCollectiveIdentifiers().stream()
                            .anyMatch(r -> r.getCollectiveIdentifierNumber().equals(secondCollectiveIdentifierNumber));
                });

        // Jedno żądanie obejmujące oba identyfikatory zbiorcze naraz.
        await().pollDelay(Duration.ZERO)
                .atMost(60, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    List<String> collectiveIdentifierNumbers = List.of(firstCollectiveIdentifierNumber, secondCollectiveIdentifierNumber);
                    CollectiveIdentifierInvoicesQueryResponse invoicesResponse = ksefClient.getCollectiveIdentifierInvoices(
                            new CollectiveIdentifierInvoicesQueryRequest(collectiveIdentifierNumbers),
                            sellerToken, null, 20);

                    Assertions.assertNotNull(invoicesResponse);

                    List<String> ksefNumberResponseList = invoicesResponse.getInvoices()
                            .stream()
                            .map(CollectiveIdentifierInvoicesQueryResponseItem::getKsefNumber)
                            .toList();
                    return ksefNumberResponseList.size() == (firstGroupKsefNumbers.size() + secondGroupKsefNumbers.size())
                            && ksefNumberResponseList.containsAll(firstGroupKsefNumbers)
                            && ksefNumberResponseList.containsAll(secondGroupKsefNumbers);
                });

        await().pollDelay(Duration.ZERO)
                .atMost(30, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    CollectiveIdentifiersQueryRequest collectiveIdentifiersQueryRequest = new CollectiveIdentifiersQueryRequest();
                    collectiveIdentifiersQueryRequest.setDateCreatedFrom(dateFrom);
                    collectiveIdentifiersQueryRequest.setDateCreatedTo(OffsetDateTime.now().plusMinutes(50));

                    CollectiveIdentifiersQueryResponse queryResponse = ksefClient.queryCollectiveIdentifiers(collectiveIdentifiersQueryRequest,
                            sellerToken, null, 20);

                    Assertions.assertNotNull(queryResponse);

                    return queryResponse.getCollectiveIdentifiers()
                            .stream()
                            .map(CollectiveIdentifiersQueryResponseItem::getCollectiveIdentifierNumber)
                            .anyMatch(e -> e.equals(firstCollectiveIdentifierNumber))
                            && queryResponse.getCollectiveIdentifiers()
                            .stream()
                            .map(CollectiveIdentifiersQueryResponseItem::getCollectiveIdentifierNumber)
                            .anyMatch(e -> e.equals(secondCollectiveIdentifierNumber));
                });
    }

    // Weryfikuje, że osoba, której nadano w kontekście sprzedawcy uprawnienia `InvoiceRead` i `CollectiveIdentifierManage`
    // przez rzeczywisty endpoint nadawania uprawnień (`POST /permissions/persons/grants`), może w tym kontekście
    // wygenerować identyfikator zbiorczy (zgodnie z wymaganymi uprawnieniami endpointu).
    // Kroki:
    // 1) Sprzedawca wystawia kilka faktur
    // 2) Sprzedawca nadaje osobie (PESEL) uprawnienia `InvoiceRead` i `CollectiveIdentifierManage` w swoim kontekście
    // 3) Oczekiwanie na zakończenie operacji nadania uprawnień
    // 4) Uwierzytelnienie tej osoby w kontekście sprzedawcy
    // 5) Wygenerowanie identyfikatora zbiorczego przy użyciu tokenu tej osoby — powinno się udać
    @Test
    void grantCollectiveIdentifierManagePermission_ThenGenerateCollectiveIdentifier() throws JAXBException, IOException, ApiException {
        String sellerNip = IdentifierGeneratorUtils.generateRandomNIP();
        String authorizedPesel = IdentifierGeneratorUtils.getRandomPesel();

        String sellerToken = authWithCustomNip(sellerNip, sellerNip).accessToken();

        String buyerNip = IdentifierGeneratorUtils.generateRandomNIP();
        List<String> ksefNumbers = sendInvoicesAndGetKsefNumbers(sellerNip, buyerNip, FIRST_GROUP_INVOICES_COUNT);

        GrantPersonPermissionsRequest request = new GrantPersonPermissionsRequestBuilder()
                .withSubjectIdentifier(new PersonPermissionsSubjectIdentifier(PersonPermissionsSubjectIdentifier.IdentifierType.PESEL, authorizedPesel))
                .withPermissions(List.of(PersonPermissionType.COLLECTIVE_IDENTIFIER_MANAGE, PersonPermissionType.INVOICEREAD))
                .withDescription("CollectiveIdentifierManage test")
                .withSubjectDetails(
                        new PersonPermissionSubjectDetails(PersonPermissionSubjectDetailsType.PERSON_BY_IDENTIFIER,
                                new PersonPermissionPersonById("Anna", "Testowa"),
                                null,
                                null
                        )
                )
                .build();
        OperationResponse grantResponse = ksefClient.grantsPermissionPerson(request, sellerToken);

        Assertions.assertNotNull(grantResponse);
        Assertions.assertNotNull(grantResponse.getReferenceNumber());

        await().pollDelay(Duration.ZERO)
                .atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .until(() -> isPermissionGranted(grantResponse.getReferenceNumber(), sellerToken));

        String authorizedToken = authWithCustomPesel(sellerNip, authorizedPesel).accessToken();

        GenerateCollectiveIdentifierResponse generateResponse = ksefClient.generateCollectiveIdentifier(
                new GenerateCollectiveIdentifierRequest(
                        ksefNumbers.stream()
                                .map(e -> {
                                    CollectiveIdentifierInvoice collectiveIdentifierInvoice = new CollectiveIdentifierInvoice();
                                    collectiveIdentifierInvoice.setKsefNumber(e);
                                    return collectiveIdentifierInvoice;
                                })
                                .toList()
                ),
                authorizedToken);

        Assertions.assertNotNull(generateResponse);
        Assertions.assertNotNull(generateResponse.getCollectiveIdentifierNumber());
    }

    private String generateCollectiveIdentifierNumber(List<String> groupKsefNumbers, String token) throws ApiException {
        GenerateCollectiveIdentifierResponse generateResponse = ksefClient.generateCollectiveIdentifier(
                new GenerateCollectiveIdentifierRequest(
                        groupKsefNumbers.stream()
                                .map(e -> {
                                    CollectiveIdentifierInvoice collectiveIdentifierInvoice = new CollectiveIdentifierInvoice();
                                    collectiveIdentifierInvoice.setKsefNumber(e);
                                    return collectiveIdentifierInvoice;
                                })
                                .toList()
                ),
                token);

        Assertions.assertNotNull(generateResponse);
        Assertions.assertNotNull(generateResponse.getCollectiveIdentifierNumber());

        return generateResponse.getCollectiveIdentifierNumber();
    }

    private Boolean isPermissionGranted(String referenceNumber, String accessToken) throws ApiException {
        PermissionStatusInfo operations = ksefClient.permissionOperationStatus(referenceNumber, accessToken);
        return operations != null && operations.getStatus().getCode() == 200;
    }

    private List<String> sendInvoicesAndGetKsefNumbers(String sellerNip, String buyerNip, int invoicesCount) throws JAXBException, IOException, ApiException {
        String accessToken = authWithCustomNip(sellerNip, sellerNip).accessToken();

        String sessionReferenceNumber = openBatchSessionAndSendInvoicesParts(sellerNip, buyerNip, accessToken,
                invoicesCount, NUMBER_OF_PARTS, CompressionType.Zip);

        closeBatchSession(sessionReferenceNumber, accessToken);

        getBatchSessionStatus(sessionReferenceNumber, accessToken, 200, invoicesCount, invoicesCount, 0);

        List<SessionInvoiceStatusResponse> documents = getInvoices(sessionReferenceNumber, invoicesCount, accessToken);

        Assertions.assertEquals(invoicesCount, documents.size());

        return documents.stream()
                .map(SessionInvoiceStatusResponse::getKsefNumber)
                .toList();
    }

    private SessionStatusResponse getBatchSessionStatus(String referenceNumber, String accessToken, int expectedStatusCode,
                                                        Integer expectedInvoiceCount, Integer expectedSuccessfulInvoiceCount,
                                                        Integer expectedFailedInvoicesCount) throws ApiException {
        await().pollDelay(Duration.ZERO)
                .atMost(60, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    SessionStatusResponse response = ksefClient.getSessionStatus(referenceNumber, accessToken);
                    return response.getStatus().getCode() == expectedStatusCode;
                });

        SessionStatusResponse response = ksefClient.getSessionStatus(referenceNumber, accessToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedInvoiceCount, response.getInvoiceCount());
        Assertions.assertEquals(expectedSuccessfulInvoiceCount, response.getSuccessfulInvoiceCount());
        Assertions.assertEquals(expectedFailedInvoicesCount, response.getFailedInvoiceCount());

        return response;
    }

    private List<SessionInvoiceStatusResponse> getInvoices(String sessionReferenceNumber, int expectedInvoicesCount, String accessToken) throws ApiException {
        SessionInvoicesResponse response = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 100,
                accessToken);

        Assertions.assertNotNull(response.getInvoices());
        Assertions.assertEquals(expectedInvoicesCount, response.getInvoices().size());
        return response.getInvoices();
    }

    private void closeBatchSession(String referenceNumber, String accessToken) throws ApiException {
        ksefClient.closeBatchSession(referenceNumber, accessToken);
    }

    private String openBatchSessionAndSendInvoicesParts(String sellerNip, String buyerNip, String accessToken, int invoicesCount, int invoicesPartCount,
                                                        CompressionType compressionType) throws IOException, ApiException {
        String invoice = new String(readBytesFromPath(invoiceTemplate), StandardCharsets.UTF_8);

        EncryptionData encryptionData = cryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(invoicesCount, sellerNip, buyerNip, invoice);

        byte[] packedFilesBytes;
        if (compressionType == null || compressionType == CompressionType.Zip) {
            byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);
            packedFilesBytes = zipBytes;
        } else {
            byte[] tarGzBytes = FilesUtil.createTarGz(invoicesInMemory);
            packedFilesBytes = tarGzBytes;
        }

        // get ZIP metadata (before crypto)
        FileMetadata packedFilesMetadata = cryptographyService.getMetaData(packedFilesBytes);

        List<byte[]> packedFilesParts = FilesUtil.splitZip(invoicesPartCount, packedFilesBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedPackedFilesParts = encryptZipParts(packedFilesParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(packedFilesMetadata, encryptedPackedFilesParts, encryptionData, compressionType);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());

        ksefClient.sendBatchParts(response, encryptedPackedFilesParts);

        return response.getReferenceNumber();
    }

    private OpenBatchSessionRequest buildOpenBatchSessionRequest(FileMetadata packedFilesMetadata, List<BatchPartSendingInfo> encryptedZipParts,
                                                                 EncryptionData encryptionData, CompressionType compressionType) {
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_3, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(packedFilesMetadata.getFileSize(), packedFilesMetadata.getHashSHA(), compressionType);

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            BatchPartSendingInfo part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1,
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        return builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector(),
                        encryptionData.encryptionInfo().getPublicKeyId()
                )
                .build();
    }

    private List<BatchPartSendingInfo> encryptZipParts(List<byte[]> packedFilesParts, byte[] cipherKey, byte[] cipherIv) {
        List<BatchPartSendingInfo> encryptedPackedFilesParts = new ArrayList<>();
        for (int i = 0; i < packedFilesParts.size(); i++) {
            byte[] encryptedPackedFilesPart = cryptographyService.encryptBytesWithAES256(
                    packedFilesParts.get(i),
                    cipherKey,
                    cipherIv
            );
            FileMetadata packedFilesPartMetadata = cryptographyService.getMetaData(encryptedPackedFilesPart);
            encryptedPackedFilesParts.add(new BatchPartSendingInfo(encryptedPackedFilesPart, packedFilesPartMetadata, (i + 1)));
        }
        return encryptedPackedFilesParts;
    }
}

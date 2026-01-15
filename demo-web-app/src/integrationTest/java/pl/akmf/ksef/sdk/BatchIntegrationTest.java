package pl.akmf.ksef.sdk;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.ExceptionDetails;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.ExceptionResponse;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.EncryptionInfo;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionResponse;
import pl.akmf.ksef.sdk.client.model.util.ZipInputStreamWithSize;
import pl.akmf.ksef.sdk.configuration.BaseIntegrationTest;
import pl.akmf.ksef.sdk.system.FilesUtil;
import pl.akmf.ksef.sdk.util.IdentifierGeneratorUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BatchIntegrationTest extends BaseIntegrationTest {
    private static final int DEFAULT_NUMBER_OF_PARTS = 2;
    private static final int DEFAULT_INVOICES_COUNT = 35;
    // Efektywny limit całej paczki: 5 GB = 5_000_000_000 bajtów
    private static final long MAX_TOTAL_PACKAGE_SIZE_IN_BYTES = 5_000_000_000L; // 5 GB (dziesiętnie)
    private static final long EXCEEDED_TOTAL_PACKAGE_SIZE_IN_BYTES = MAX_TOTAL_PACKAGE_SIZE_IN_BYTES + 1; // 5 GB + 1 bajt
    // Jednostki komunikowane i egzekwowane przez API w MB/GB (dziesiętne: 1 MB = 1_000_000 B, 1 GB = 1_000_000_000 B).
    private static final long PADDING_SAFETY_MARGIN_IN_BYTES = 100_000; // +100 KB zapasu (dziesiętne)
    // Efektywny limit na część przed szyfrowaniem: 100 MB = 100_000_000 bajtów
    private static final long MAX_PART_SIZE_IN_BYTES = 100_000_000L; // 100 MB (dziesiętnie)
    private static final int MAX_PART_COUNT_LIMIT = 50;
    private static final int EXCEEDING_PART_COUNT = MAX_PART_COUNT_LIMIT + 1;
    private static final int MAX_INVOICE_COUNT_LIMIT = 10_000;
    private static final int EXCEEDING_INVOICE_COUNT = MAX_INVOICE_COUNT_LIMIT + 1;

    private static final String PATH_SAMPLE_INVOICE_TEMPLATE_XML = "/xml/invoices/sample/invoice-template.xml";
    private static final int STATUS_CODE_INVALID_INVOICES = 445;
    private static final int STATUS_CODE_EXCEEDED_INVOICE_LIMIT = 420;
    private static final int STATUS_CODE_INVALID_ENCRYPTION_KEY = 415;
    private static final int STATUS_CODE_DECRYPTION_ERROR = 405;
    private static final int STATUS_CODE_INVALID_INITIALIZATION_VECTOR = 430;

    @Autowired
    private DefaultCryptographyService defaultCryptographyService;

    // Weryfikuje poprawne wysłanie dokumentów w paczce (scenariusz pozytywny).
    // Oczekuje pomyślnego przetworzenia wszystkich faktur i możliwości pobrania UPO.
    @Test
    void batchSessionE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String sessionReferenceNumber = openBatchSessionAndSendInvoicesParts(contextNip, accessToken, DEFAULT_INVOICES_COUNT, DEFAULT_NUMBER_OF_PARTS);

        closeSession(sessionReferenceNumber, accessToken);

        String upoReferenceNumber = getBatchSessionStatus(sessionReferenceNumber, accessToken);

        List<SessionInvoiceStatusResponse> documents = getInvoice(sessionReferenceNumber, accessToken);

        getBatchInvoiceAndUpo(sessionReferenceNumber, documents.getFirst().getKsefNumber(), accessToken);

        getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);
    }

    // Weryfikuje poprawne wysłanie dokumentów w paczce (scenariusz pozytywny).
    // Oczekuje pomyślnego przetworzenia wszystkich faktur i możliwości pobrania UPO.
    @Test
    void batchSessionStreamE2EIntegrationTest() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String sessionReferenceNumber = openBatchSessionAndSendInvoicesPartsStream(contextNip, accessToken, DEFAULT_INVOICES_COUNT, DEFAULT_NUMBER_OF_PARTS);

        closeSession(sessionReferenceNumber, accessToken);

        String upoReferenceNumber = getBatchSessionStatus(sessionReferenceNumber, accessToken);

        List<SessionInvoiceStatusResponse> documents = getInvoice(sessionReferenceNumber, accessToken);

        getBatchInvoiceAndUpo(sessionReferenceNumber, documents.getFirst().getKsefNumber(), accessToken);

        getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);
    }

    // Weryfikuje odrzucenie faktur z niepoprawnym NIP (scenariusz negatywny).
    // Oczekuje statusu błędu i zliczenia wszystkich faktur jako niepoprawnych.
    @Test
    void shouldThrowWhileSendWithIncorrectNip() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        // Generowanie faktury z NIP-em innym niż użyty do uwierzytelnienia
        String unauthorizedNip = IdentifierGeneratorUtils.generateRandomNIP();
        String sessionReferenceNumber = openBatchSessionAndSendInvoicesParts(unauthorizedNip, accessToken, DEFAULT_INVOICES_COUNT, DEFAULT_NUMBER_OF_PARTS);

        closeSession(sessionReferenceNumber, accessToken);

        // Kod 445 Błąd weryfikacji, brak poprawnych faktur
        SessionStatusResponse batchSessionStatus = getBatchSessionStatus(sessionReferenceNumber, accessToken, STATUS_CODE_INVALID_INVOICES,
                DEFAULT_INVOICES_COUNT, 0, DEFAULT_INVOICES_COUNT);
        Assertions.assertTrue(batchSessionStatus.getStatus().getDescription()
                .contains("Błąd weryfikacji, brak poprawnych faktur"));
    }

    // Weryfikuje odrzucenie paczki przekraczającej limit 10000 faktur.
    // Oczekuje zwrócenia błędu o przekroczonym limicie.
    @Test
    void shouldThrowWhileSendExceedingInvoiceCount() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        int partsCount = 1;

        String sessionReferenceNumber = openBatchSessionAndSendInvoicesParts(contextNip, accessToken, EXCEEDING_INVOICE_COUNT, partsCount);

        closeSession(sessionReferenceNumber, accessToken);

        // Kod 420 Przekroczony limit faktur w sesji
        SessionStatusResponse batchSessionStatus = getBatchSessionStatus(sessionReferenceNumber, accessToken, STATUS_CODE_EXCEEDED_INVOICE_LIMIT,
                null, null, null);
        Assertions.assertTrue(batchSessionStatus.getStatus().getDetails().get(0)
                .contains("Przekroczono dopuszczalną liczbę faktur w paczce: '10000'. Liczba faktur w paczce: '10001'."));
        Assertions.assertTrue(batchSessionStatus.getStatus().getDescription()
                .contains("Przekroczony limit faktur w sesji"));
    }

    // Weryfikuje odrzucenie paczki przekraczającej maksymalny rozmiar 5 GiB.
    // Oczekuje wyjątku podczas próby otwarcia sesji z fileSize > 5368709120 bajtów (MaxTotalPackageSizeInBytes).
    @Test
    void shouldThrowWhileSendWithExceededTotalPackageSize() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        int partsCount = 1;

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);
        // Modyfikacja metadaty aby symulować paczkę o rozmiarze przekraczającym 5 GiB
        zipMetadata.setFileSize(EXCEEDED_TOTAL_PACKAGE_SIZE_IN_BYTES);

        List<byte[]> zipParts = FilesUtil.splitZip(partsCount, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        // Użycie zmanipulowanych metadanych z fileSize > 5 GiB
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, encryptionData);

        // API KSeF powinno odrzucić żądanie ze względu na przekroczony limit fileSize
        ApiException apiException = assertThrows(ApiException.class, () ->
                ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21405, details.getExceptionCode());
        Assertions.assertEquals("Błąd walidacji danych wejściowych.", details.getExceptionDescription());
        Assertions.assertEquals("'fileSize' must be less than or equal to '5000000000'.", details.getDetails().getFirst());
    }

    // Weryfikuje odrzucenie paczki przekraczającej limit rozmiaru 100 MiB (przed szyfrowaniem).
    // Oczekuje wyjątku podczas próby otwarcia sesji.
    @Test
    void shouldThrowWhileSendWithExceededPartSize() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();
        int partsCount = 1;

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        // Dodanie sztucznego wypełnienia, aby paczka przekroczyła 100 MiB
        // Limit KSeF to 100 MiB dla pojedynczej części PRZED szyfrowaniem
        byte[] paddedZipBytes = addPaddingToZipArchive(zipBytes, MAX_PART_SIZE_IN_BYTES);

        List<byte[]> zipParts = FilesUtil.splitZip(partsCount, paddedZipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, encryptionData);

        // API KSeF odrzuca żądanie już na etapie otwarcia sesji
        ApiException apiException = assertThrows(ApiException.class, () ->
                ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21157, details.getExceptionCode());
        Assertions.assertEquals("Nieprawidłowy rozmiar części pakietu.", details.getExceptionDescription());
        Assertions.assertEquals("Rozmiar części 1 przekroczył dozwolony rozmiar 100MB.", details.getDetails().getFirst());
    }

    // Weryfikuje wykrycie próby zamknięcia sesji bez wysłania wszystkich zadeklarowanych części.
    // Oczekuje wyjątku podczas wysyłania niepełnego zestawu części.
    @Test
    void shouldThrowWhileCloseSessionWithoutAllParts() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        List<byte[]> zipParts = FilesUtil.splitZip(DEFAULT_NUMBER_OF_PARTS, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, encryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        String sessionReferenceNumber = response.getReferenceNumber();
        Assertions.assertNotNull(sessionReferenceNumber);

        // Próba wysłania tylko pierwszej części, mimo że zadeklarowano 5
        // API powinno wykryć niezgodność i odrzucić żądanie
        List<BatchPartSendingInfo> incompletePartsList = List.of(encryptedZipParts.get(0));

        ksefClient.sendBatchParts(response, incompletePartsList);

        ApiException apiException = assertThrows(ApiException.class, () ->
                closeSession(sessionReferenceNumber, accessToken));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21205, details.getExceptionCode());
        Assertions.assertEquals("Pakiet nie może być pusty.", details.getExceptionDescription());
        Assertions.assertEquals("Nie przesłano zadeklarowanej '2' części pliku.", details.getDetails().getFirst());
    }

    // Weryfikuje odrzucenie paczki z liczbą części przekraczającą maksymalny limit 50.
    // Oczekuje wyjątku podczas próby otwarcia sesji.
    @Test
    void shouldThrowWhileSendWithExceededPartCount() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(150, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        // Próba podziału paczki na 51 części, co przekracza limit API wynoszący 50
        List<byte[]> zipParts = FilesUtil.splitZip(EXCEEDING_PART_COUNT, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, encryptionData);

        // API KSeF odrzuca żądanie z przekroczoną liczbą części
        ApiException apiException = assertThrows(ApiException.class, () ->
                ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken));
        ExceptionResponse exceptionResponse = apiException.getExceptionResponse();
        Assertions.assertFalse(exceptionResponse.getException().getExceptionDetailList().isEmpty());
        ExceptionDetails details = exceptionResponse.getException().getExceptionDetailList().getFirst();
        Assertions.assertEquals(21161, details.getExceptionCode());
        Assertions.assertEquals("Przekroczono dozwoloną liczbę części pakietów.", details.getExceptionDescription());
        Assertions.assertEquals("Liczba części pliku musi być mniejsza lub równa niż 50.", details.getDetails().getFirst());
    }

    // Weryfikuje wykrycie nieprawidłowo zaszyfrowanego klucza symetrycznego.
    // Oczekuje błędu deszyfrowania po przetworzeniu sesji przez system KSeF.
    @Test
    void shouldThrowWhileSendWithInvalidEncryptedKey() throws JAXBException, IOException, ApiException, NoSuchAlgorithmException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        List<byte[]> zipParts = FilesUtil.splitZip(DEFAULT_NUMBER_OF_PARTS, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        EncryptionInfo corruptedEncryptionInfo = new EncryptionInfo();
        // Podmiana prawidłowego klucza zaszyfrowanego RSA na losowe dane
        // Klucz musi być zaszyfrowany RSA-OAEP kluczem publicznym MF, więc losowe dane nie będą poprawne
        int encryptionKeySize = 256; // bytes dla RSA
        byte[] corruptedEncryptedKey = new byte[encryptionKeySize];
        new SecureRandom().nextBytes(corruptedEncryptedKey);
        String corruptedEncryptedSymmetricKey = Base64.getEncoder().encodeToString(corruptedEncryptedKey);
        corruptedEncryptionInfo.setEncryptedSymmetricKey(corruptedEncryptedSymmetricKey);
        corruptedEncryptionInfo.setInitializationVector(encryptionData.encryptionInfo().getInitializationVector());
        EncryptionData corruptedEncryptionData = new EncryptionData(encryptionData.cipherKey(), encryptionData.cipherIv(),
                corruptedEncryptedSymmetricKey, corruptedEncryptionInfo);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, corruptedEncryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());
        String sessionReferenceNumber = response.getReferenceNumber();

        ksefClient.sendBatchParts(response, encryptedZipParts);

        closeSession(sessionReferenceNumber, accessToken);

        // Kod 415 Błąd odszyfrowania dostarczonego klucza
        SessionStatusResponse batchSessionStatus = getBatchSessionStatus(sessionReferenceNumber, accessToken, STATUS_CODE_INVALID_ENCRYPTION_KEY,
                null, null, null);
        Assertions.assertTrue(batchSessionStatus.getStatus().getDetails().get(0)
                .contains("Rozszyfrowania klucza symetrycznego zakończone błędem"));
        Assertions.assertTrue(batchSessionStatus.getStatus().getDescription()
                .contains("Błąd odszyfrowania dostarczonego klucza"));
    }

    // Weryfikuje wykrycie uszkodzonych zaszyfrowanych danych.
    // Oczekuje błędu deszyfrowania po przetworzeniu sesji przez system KSeF.
    @Test
    void shouldThrowWhileSendWithCorruptedEncryptedData() throws JAXBException, IOException, ApiException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        List<byte[]> zipParts = FilesUtil.splitZip(DEFAULT_NUMBER_OF_PARTS, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Celowe uszkodzenie zaszyfrowanych danych poprzez inwersję bitów w środkowej pozycji
        // To symuluje uszkodzenie podczas transmisji lub manipulację danymi
        byte[] corruptedData = encryptedZipParts.get(0).getData();
        int corruptionPosition = corruptedData.length / 2;
        corruptedData[corruptionPosition] ^= (byte) 0xFF;
        BatchPartSendingInfo corruptedPart = new BatchPartSendingInfo(
                corruptedData,
                encryptedZipParts.getFirst().getMetadata(),
                encryptedZipParts.getFirst().getOrdinalNumber()
        );
        List<BatchPartSendingInfo> corruptedZipParts = List.of(corruptedPart);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, corruptedZipParts, encryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());
        String sessionReferenceNumber = response.getReferenceNumber();

        ksefClient.sendBatchParts(response, encryptedZipParts);

        closeSession(sessionReferenceNumber, accessToken);

        // Kod 405 Błąd weryfikacji poprawności dostarczonych elementów paczki
        SessionStatusResponse batchSessionStatus = getBatchSessionStatus(sessionReferenceNumber, accessToken, STATUS_CODE_DECRYPTION_ERROR,
                null, null, null);
        Assertions.assertTrue(batchSessionStatus.getStatus().getDetails().get(0)
                .contains("Skrót SHA-256 części: '1' nie zgadza się z zadeklarowanym skrótem:"));
        Assertions.assertTrue(batchSessionStatus.getStatus().getDescription()
                .contains("Błąd weryfikacji poprawności dostarczonych elementów paczki"));
    }

    // Weryfikuje wykrycie nieprawidłowego wektora inicjującego (IV).
    // Oczekuje błędu deszyfrowania po przetworzeniu sesji przez system KSeF.
    @Test
    void shouldThrowWhileSendWithInvalidInitializationVector() throws JAXBException, IOException, ApiException, NoSuchAlgorithmException {
        String contextNip = IdentifierGeneratorUtils.generateRandomNIP();
        String accessToken = authWithCustomNip(contextNip, contextNip).accessToken();

        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(DEFAULT_INVOICES_COUNT, contextNip, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        List<byte[]> zipParts = FilesUtil.splitZip(DEFAULT_NUMBER_OF_PARTS, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        EncryptionInfo corruptedEncryptionInfo = new EncryptionInfo();
        // Generowanie losowego IV zamiast użycia tego, który wykorzystano przy szyfrowaniu
        // W AES-CBC poprawny IV jest kluczowy dla odszyfrowania pierwszego bloku
        int InitializationVectorSize = 16; // bytes
        byte[] corruptedInitializationVector = new byte[InitializationVectorSize];
        new SecureRandom().nextBytes(corruptedInitializationVector);
        corruptedEncryptionInfo.setEncryptedSymmetricKey(encryptionData.encryptionInfo().getEncryptedSymmetricKey());
        corruptedEncryptionInfo.setInitializationVector(Base64.getEncoder().encodeToString(corruptedInitializationVector));
        EncryptionData corruptedEncryptionData = new EncryptionData(encryptionData.cipherKey(), encryptionData.cipherIv(),
                encryptionData.encryptedCipherKey(), corruptedEncryptionInfo);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, corruptedEncryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());
        String sessionReferenceNumber = response.getReferenceNumber();

        ksefClient.sendBatchParts(response, encryptedZipParts);

        closeSession(sessionReferenceNumber, accessToken);

        // Kod 430 Błąd dekompresji pierwotnego archiwum
        SessionStatusResponse batchSessionStatusResponse = getBatchSessionStatus(sessionReferenceNumber, accessToken, STATUS_CODE_INVALID_INITIALIZATION_VECTOR,
                null, null, null);
        Assertions.assertTrue(batchSessionStatusResponse.getStatus().getDetails().get(0)
                .contains("Skrót SHA-256 przesłanej paczki faktur nie jest zgodny z zadeklarowanym skrótem:"));
    }

    private void getSessionUpo(String sessionReferenceNumber, String upoReferenceNumber, String accessToken) throws ApiException {

        byte[] sessionUpo = ksefClient.getSessionUpo(sessionReferenceNumber, upoReferenceNumber, accessToken);

        Assertions.assertNotNull(sessionUpo);
    }

    private void getBatchInvoiceAndUpo(String sessionReferenceNumber, String ksefNumber, String accessToken) throws ApiException {
        byte[] upoResponse = ksefClient.getSessionInvoiceUpoByKsefNumber(sessionReferenceNumber, ksefNumber, accessToken);

        Assertions.assertNotNull(upoResponse);
    }

    private List<SessionInvoiceStatusResponse> getInvoice(String sessionReferenceNumber, String accessToken) throws ApiException {
        SessionInvoicesResponse response = ksefClient.getSessionInvoices(sessionReferenceNumber, null, 100,
                accessToken);

        Assertions.assertNotNull(response.getInvoices());
        Assertions.assertEquals(DEFAULT_INVOICES_COUNT, response.getInvoices().size());
        return response.getInvoices();
    }

    private String getBatchSessionStatus(String referenceNumber, String accessToken) throws ApiException {
        return getBatchSessionStatus(referenceNumber, accessToken, 200,
                DEFAULT_INVOICES_COUNT, DEFAULT_INVOICES_COUNT, 0)
                .getUpo().getPages().getFirst().getReferenceNumber();
    }

    private SessionStatusResponse getBatchSessionStatus(String referenceNumber, String accessToken, int expectedStatusCode,
                                                        Integer expectedInvoiceCount, Integer expectedSuccessfulInvoiceCount,
                                                        Integer expectedFailedInvoicesCount) throws ApiException {
        await().atMost(30, SECONDS)
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

    private void closeSession(String referenceNumber, String accessToken) throws ApiException {
        ksefClient.closeBatchSession(referenceNumber, accessToken);
    }

    private String openBatchSessionAndSendInvoicesParts(String context, String accessToken, int invoicesCount, int partsCount) throws IOException, ApiException {
        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(invoicesCount, context, invoice);

        byte[] zipBytes = FilesUtil.createZip(invoicesInMemory);

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        List<byte[]> zipParts = FilesUtil.splitZip(partsCount, zipBytes);

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = encryptZipParts(zipParts, encryptionData.cipherKey(), encryptionData.cipherIv());

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequest(zipMetadata, encryptedZipParts, encryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());

        ksefClient.sendBatchParts(response, encryptedZipParts);

        return response.getReferenceNumber();
    }

    // Dodaje wypełnienie (padding) do archiwum ZIP, aby osiągnąć minimalny wymagany rozmiar.
    // Używane do testowania limitów rozmiaru paczki.
    // Wypełnienie składa się z losowych danych w pliku bez kompresji, aby zachować kontrolę nad rozmiarem.
    // </summary>
    // <param name="zipBytes">Oryginalne bajty archiwum ZIP.</param>
    // <param name="minimumSizeInBytes">Minimalny wymagany rozmiar w bajtach.</param>
    // <returns>Archiwum ZIP z dodanym wypełnieniem.</returns>
    private byte[] addPaddingToZipArchive(byte[] zipBytes, long minSizeBytes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(zipBytes);

        long currentSize = baos.size();

        if (currentSize < minSizeBytes) {
            long paddingSize = minSizeBytes - currentSize + PADDING_SAFETY_MARGIN_IN_BYTES; // +100 KB zapasu
            byte[] paddingData = new byte[(int) paddingSize];
            new SecureRandom().nextBytes(paddingData);

            try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
                ZipEntry paddingEntry = new ZipEntry("padding.bin");
                paddingEntry.setMethod(ZipEntry.STORED);
                paddingEntry.setSize(paddingData.length);

                CRC32 crc = new CRC32();
                crc.update(paddingData);
                paddingEntry.setCrc(crc.getValue());

                zipOut.putNextEntry(paddingEntry);
                zipOut.write(paddingData);
                zipOut.closeEntry();
            }
        }

        return baos.toByteArray();
    }

    private String openBatchSessionAndSendInvoicesPartsStream(String context, String accessToken, int invoicesCount, int invoicesPartCount) throws IOException, ApiException {
        String invoice = new String(readBytesFromPath(PATH_SAMPLE_INVOICE_TEMPLATE_XML), StandardCharsets.UTF_8);

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        Map<String, byte[]> invoicesInMemory = FilesUtil.generateInvoicesInMemory(invoicesCount, context, invoice);

        ZipInputStreamWithSize zipInputStreamWithSize = FilesUtil.createZipInputStream(invoicesInMemory);
        InputStream zipInputStream = zipInputStreamWithSize.getInputStream();
        int zipLength = zipInputStreamWithSize.getZipLength();

        // get ZIP metadata (before crypto)
        FileMetadata zipMetadata = defaultCryptographyService.getMetaData(zipInputStream);
        zipInputStream.reset();

        List<BatchPartStreamSendingInfo> encryptedStreamParts = FilesUtil.splitAndEncryptZipStream(zipInputStream, invoicesPartCount, zipLength, encryptionData.cipherKey(),
                encryptionData.cipherIv(), defaultCryptographyService);

        // Build request
        OpenBatchSessionRequest request = buildOpenBatchSessionRequestForStream(zipMetadata, encryptedStreamParts, encryptionData);

        OpenBatchSessionResponse response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, accessToken);
        Assertions.assertNotNull(response.getReferenceNumber());

        ksefClient.sendBatchPartsWithStream(response, encryptedStreamParts);

        return response.getReferenceNumber();
    }

    private List<BatchPartSendingInfo> encryptZipParts(List<byte[]> zipParts, byte[] cipherKey, byte[] cipherIv) {
        List<BatchPartSendingInfo> encryptedZipParts = new ArrayList<>();
        for (int i = 0; i < zipParts.size(); i++) {
            byte[] encryptedZipPart = defaultCryptographyService.encryptBytesWithAES256(
                    zipParts.get(i),
                    cipherKey,
                    cipherIv
            );
            FileMetadata zipPartMetadata = defaultCryptographyService.getMetaData(encryptedZipPart);
            encryptedZipParts.add(new BatchPartSendingInfo(encryptedZipPart, zipPartMetadata, (i + 1)));
        }
        return encryptedZipParts;
    }

    private OpenBatchSessionRequest buildOpenBatchSessionRequest(FileMetadata zipMetadata, List<BatchPartSendingInfo> encryptedZipParts, EncryptionData encryptionData) {
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            BatchPartSendingInfo part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1,
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        return builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();
    }

    private OpenBatchSessionRequest buildOpenBatchSessionRequestForStream(FileMetadata zipMetadata, List<BatchPartStreamSendingInfo> encryptedZipParts, EncryptionData encryptionData) {
        OpenBatchSessionRequestBuilder builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            BatchPartStreamSendingInfo part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1,
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        return builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();
    }
}

package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.builders.batch.OpenBatchSessionRequestBuilder;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.UpoVersion;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.SchemaVersion;
import pl.akmf.ksef.sdk.client.model.session.SessionValue;
import pl.akmf.ksef.sdk.client.model.session.SystemCode;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartSendingInfo;
import pl.akmf.ksef.sdk.client.model.session.batch.OpenBatchSessionRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchSessionController {
    private final DefaultKsefClient ksefClient;
    private final DefaultCryptographyService defaultCryptographyService;
    private static final String TMP = "tmp";
    private static final Path INVOICES_DIR = Paths.get(TMP + "/Invoices");

    @PostMapping("/open-session/{contextIdentifier}")
    public String openBatchSession(@PathVariable String contextIdentifier,
                                   @RequestParam(value = "numberOfParts", defaultValue = "11") int numberOfParts,
                                   @RequestParam(value = "maxPartSizeMB", defaultValue = "0.01") double maxPartSizeMB,
                                   @RequestHeader(name = AUTHORIZATION) String authToken
    ) throws Exception {
        String invoicePath = "demo-web-app/src/main/resources/xml/invoices/sample/invoice-template.xml";
        int singleXmlSizeBytes = Files.readString(Paths.get(invoicePath), StandardCharsets.UTF_8).length();
        log.info("singleXmlSizeBytes " + singleXmlSizeBytes + ", " + (singleXmlSizeBytes / 1024) + " KB, " + (singleXmlSizeBytes / 1024 / 1024) + " MB");
        double totalZipSizeBytes = maxPartSizeMB * numberOfParts * 1024 * 1024;
        int invoicesCount = (int) (totalZipSizeBytes / singleXmlSizeBytes);

        log.info("for " + invoicePath + " template and " + numberOfParts + " parts and max part size " + maxPartSizeMB + " MB" + (maxPartSizeMB < 1 ? " " + formatSize(maxPartSizeMB) : "") + " you need " + invoicesCount + " invoices");

        EncryptionData encryptionData = defaultCryptographyService.getEncryptionData();

        if (!Files.exists(INVOICES_DIR)) Files.createDirectories(INVOICES_DIR);

        List<Path> invoices = new ArrayList<>();
        for (int i = 0; i < invoicesCount; i++) {
            String invoiceTemplate = Files.readString(Paths.get(invoicePath), StandardCharsets.UTF_8)
                    .replace("#nip#", contextIdentifier)
                    .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .replace("#invoice_number#", UUID.randomUUID().toString());

            Path invoiceFile = INVOICES_DIR.resolve("faktura_" + (i + 1) + ".xml");
            Files.writeString(invoiceFile, invoiceTemplate);
            invoices.add(invoiceFile);
        }
        log.info("prepared " + invoicesCount + " invoices");

        // create ZIP in memory
        byte[] zipBytes;
        try (ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
             ZipOutputStream archive = new ZipOutputStream(zipStream)) {

            for (Path file : invoices) {
                archive.putNextEntry(new ZipEntry(file.getFileName().toString()));
                byte[] fileContent = Files.readAllBytes(file);
                archive.write(fileContent);
                archive.closeEntry();
            }
            archive.finish();
            zipBytes = zipStream.toByteArray();
        }
        log.info("created zip in memory - length " + zipBytes.length + ", " + (zipBytes.length / 1024) + " KB, " + (zipBytes.length / (1024 * 1024)) + " MB");

        // get ZIP metadata (before crypto)
        var zipMetadata = defaultCryptographyService.getMetaData(zipBytes);

        // Split ZIP into ${numberOfParts} parts
        int partSize = (int) Math.ceil((double) zipBytes.length / numberOfParts);

        List<byte[]> zipParts = new ArrayList<>();
        for (int i = 0; i < numberOfParts; i++) {
            int start = i * partSize;
            int size = Math.min(partSize, zipBytes.length - start);
            if (size <= 0) break;

            byte[] part = Arrays.copyOfRange(zipBytes, start, start + size);
            log.info("part " + i + " size " + part.length + ", " + (part.length / 1024) + " KB, " + (part.length / (1024 * 1024)) + " MB");
            zipParts.add(part);
        }
        log.info("finish split zip into " + numberOfParts + " parts");

        // Encrypt zip parts
        List<BatchPartSendingInfo> encryptedZipParts = new ArrayList<>();
        for (int i = 0; i < zipParts.size(); i++) {
            byte[] encryptedZipPart = defaultCryptographyService.encryptBytesWithAES256(
                    zipParts.get(i),
                    encryptionData.cipherKey(),
                    encryptionData.cipherIv()
            );
            FileMetadata zipPartMetadata = defaultCryptographyService.getMetaData(encryptedZipPart);
            encryptedZipParts.add(new BatchPartSendingInfo(encryptedZipPart, zipPartMetadata, (i + 1)));
        }
        log.info("encrypted parts " + encryptedZipParts.size());

        // Build request
        var builder = OpenBatchSessionRequestBuilder.create()
                .withFormCode(SystemCode.FA_2, SchemaVersion.VERSION_1_0E, SessionValue.FA)
                .withOfflineMode(false)
                .withBatchFile(zipMetadata.getFileSize(), zipMetadata.getHashSHA());

        for (int i = 0; i < encryptedZipParts.size(); i++) {
            var part = encryptedZipParts.get(i);
            builder = builder.addBatchFilePart(i + 1,
                    part.getMetadata().getFileSize(), part.getMetadata().getHashSHA());
        }

        OpenBatchSessionRequest request = builder.endBatchFile()
                .withEncryption(
                        encryptionData.encryptionInfo().getEncryptedSymmetricKey(),
                        encryptionData.encryptionInfo().getInitializationVector()
                )
                .build();

        var response = ksefClient.openBatchSession(request, UpoVersion.UPO_4_3, authToken);
        log.info("batch session opened " + response);
        ksefClient.sendBatchParts(response, encryptedZipParts);
        log.info("all parts send");

        deleteDirectoryRecursively(Paths.get(TMP));

        log.info("Wysłano, zamknij sesję, żeby zacząć przetarzanie i sprawdź status sesji: " + response.getReferenceNumber());
        return "Wysłano, zamknij sesję, żeby zacząć przetarzanie i sprawdź status sesji: " + response.getReferenceNumber();
    }

    @PostMapping("/close-session")
    public void closeBatchSession(@RequestParam String referenceNumber,
                                  @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        ksefClient.closeBatchSession(referenceNumber, authToken);

        log.info("zamknięto sesje o nr ref " + referenceNumber);
    }

    private void deleteDirectoryRecursively(Path path) {
        if (Files.exists(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.printf("Nie udało się usunąć: %s (%s)%n", p, e.getMessage());
                            }
                        });
            } catch (IOException e) {
                System.err.printf("Błąd przy czyszczeniu folderu: %s (%s)%n", path, e.getMessage());
            }
        }
    }

    private static String formatSize(double sizeInMB) {
        if (sizeInMB >= 1024) {
            double sizeInGB = sizeInMB / 1024;
            return String.format("%.2f GB", sizeInGB);
        } else if (sizeInMB >= 1) {
            return String.format("%.2f MB", sizeInMB);
        } else {
            double sizeInKB = sizeInMB * 1024;
            if (sizeInKB >= 1) {
                return String.format("%.2f KB", sizeInKB);
            } else {
                double sizeInBytes = sizeInKB * 1024;
                return String.format("%.2f B", sizeInBytes);
            }
        }
    }
}

package pl.akmf.ksef.sdk.system;

import org.apache.commons.lang3.function.TriFunction;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackagePart;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.util.ZipInputStreamWithSize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FilesUtil {
    private FilesUtil() {
    }

    public static Map<String, byte[]> generateInvoicesInMemory(int invoicesCount, String contextNip, String invoiceTemplate) {
        Map<String, byte[]> invoiceMap = new HashMap<>();
        for (int i = 0; i < invoicesCount; i++) {
            String invoice = invoiceTemplate
                    .replace("#nip#", contextNip)
                    .replace("#invoicing_date#", LocalDate.of(2025, 6, 15).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .replace("#invoice_number#", UUID.randomUUID().toString());

            invoiceMap.put("faktura_" + (i + 1) + ".xml", invoice.getBytes(StandardCharsets.UTF_8));
        }
        return invoiceMap;
    }

    public static List<BatchPartStreamSendingInfo> splitAndEncryptZipStream(
            InputStream zipInputStream,
            int invoicesPartCount,
            int zipLength,
            byte[] cipherKey,
            byte[] cipherIv,
            DefaultCryptographyService cryptographyService) throws IOException {
        List<BatchPartStreamSendingInfo> encryptedStreamParts = new ArrayList<>();

        // Split ZIP into ${invoicesPartCount} parts
        int partSize = (int) Math.ceil((double) zipLength / invoicesPartCount);

        byte[] buffer = new byte[partSize];
        int bytesRead;
        int partIndex = 0;

        while ((bytesRead = zipInputStream.read(buffer)) != -1 && partIndex < invoicesPartCount) {
            ByteArrayInputStream partInputStream = new ByteArrayInputStream(buffer, 0, bytesRead);

            ByteArrayOutputStream encryptedBaos = new ByteArrayOutputStream();
            cryptographyService.encryptStreamWithAES256(
                    partInputStream,
                    encryptedBaos,
                    cipherKey,
                    cipherIv
            );

            byte[] encryptedBytes = encryptedBaos.toByteArray();
            ByteArrayInputStream encryptedInputStream = new ByteArrayInputStream(encryptedBytes);

            FileMetadata metadata = cryptographyService.getMetaData(encryptedInputStream);
            encryptedInputStream.reset();

            encryptedStreamParts.add(new BatchPartStreamSendingInfo(
                    encryptedInputStream,
                    metadata,
                    partIndex + 1
            ));
            partIndex++;
        }

        return encryptedStreamParts;
    }

    public static List<byte[]> splitZip(int partsCount, byte[] zipBytes) {
        // Split ZIP into ${partsCount} parts
        int partSize = (int) Math.ceil((double) zipBytes.length / partsCount);

        List<byte[]> zipParts = new ArrayList<>();
        for (int i = 0; i < partsCount; i++) {
            int start = i * partSize;
            int size = Math.min(partSize, zipBytes.length - start);
            if (size <= 0) break;

            byte[] part = Arrays.copyOfRange(zipBytes, start, start + size);
            zipParts.add(part);
        }
        return zipParts;
    }

    public static byte[] createZip(Map<String, byte[]> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Map of files cannot be null or empty.");
        }

        byte[] zipBytes;
        try (ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
             ZipOutputStream archive = new ZipOutputStream(zipStream)) {

            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                String fileName = entry.getKey();
                byte[] fileContent = entry.getValue();

                if (fileName == null || fileContent == null) {
                    continue; // skip incorrect data
                }

                archive.putNextEntry(new ZipEntry(fileName));
                archive.write(fileContent);
                archive.closeEntry();
            }

            archive.finish();
            zipBytes = zipStream.toByteArray();
        }

        return zipBytes;
    }

    public static ZipInputStreamWithSize createZipInputStream(Map<String, byte[]> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Map of files cannot be null or empty.");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(baos)) {

            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                String fileName = entry.getKey();
                byte[] content = entry.getValue();

                if (fileName == null || content == null) {
                    continue; // skip incorrect data
                }

                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                zipOut.write(content);
                zipOut.closeEntry();
            }

            zipOut.finish();
            zipOut.flush();

            byte[] zipBytes = baos.toByteArray();
            int zipLength = zipBytes.length;
            return new ZipInputStreamWithSize(new ByteArrayInputStream(zipBytes), zipLength);
        }
    }

    public static Map<String, String> unzip(byte[] zipBytes) throws IOException {
        Map<String, String> files = new HashMap<>();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
             ZipInputStream zis = new ZipInputStream(bais, StandardCharsets.UTF_8)) {

            ZipEntry entry;
            byte[] buffer = new byte[8192];

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().isBlank()) {
                    continue;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int bytesRead;
                while ((bytesRead = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

                String content = baos.toString(StandardCharsets.UTF_8);
                files.put(entry.getName(), content);

                zis.closeEntry();
            }
        }

        return files;
    }

    public static byte[] mergeZipParts(EncryptionData encryptionData, List<InvoicePackagePart> parts,
                                       Function<InvoicePackagePart, byte[]> downloadPackagePartFunction,
                                       TriFunction<byte[], byte[], byte[], byte[]> decryptBytesWithAes256TriFunction) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (InvoicePackagePart part : parts) {
                byte[] encryptedPackagePart = downloadPackagePartFunction.apply(part);
                byte[] decryptBytesPackagePart = decryptBytesWithAes256TriFunction.apply(encryptedPackagePart,
                        encryptionData.cipherKey(),
                        encryptionData.cipherIv()
                );

                if (decryptBytesPackagePart != null && decryptBytesPackagePart.length > 0) {
                    outputStream.write(decryptBytesPackagePart);
                }
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

package pl.akmf.ksef.sdk.system;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.function.TriFunction;
import pl.akmf.ksef.sdk.client.interfaces.CryptographyService;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicePackagePart;
import pl.akmf.ksef.sdk.client.model.session.EncryptionData;
import pl.akmf.ksef.sdk.client.model.session.FileMetadata;
import pl.akmf.ksef.sdk.client.model.session.batch.BatchPartStreamSendingInfo;
import pl.akmf.ksef.sdk.client.model.util.ZipInputStreamWithSize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FilesUtil {

    private static final int TAR_BLOCK_SIZE = 512;

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
            long zipLength,
            byte[] cipherKey,
            byte[] cipherIv,
            CryptographyService cryptographyService) throws IOException {
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
            throw new SystemKSeFSDKException(e.getMessage());
        }
    }

    public static byte[] createTarGz(Map<String, byte[]> files) throws IOException {
        Objects.requireNonNull(files, "files");

        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(output)) {

            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                String fileName = entry.getKey();
                byte[] content = entry.getValue();
                if (fileName == null || fileName.isBlank()) {
                    throw new IllegalArgumentException("fileName is null or blank");
                }
                Objects.requireNonNull(content, "content");

                writeTarEntry(gzip, fileName, content);
            }

            // 2 puste bloki kończące TAR
            gzip.write(new byte[TAR_BLOCK_SIZE]);
            gzip.write(new byte[TAR_BLOCK_SIZE]);

            gzip.finish(); // ważne, żeby domknąć GZIP

            return output.toByteArray();
        }
    }

    public static Map<String, String> extractTarGz(byte[] tarGzBytes) {
        if (tarGzBytes == null || tarGzBytes.length == 0) {
            throw new IllegalArgumentException("Input tar.gz cannot be null or empty");
        }

        Map<String, String> files = new LinkedHashMap<>();

        try (
                ByteArrayInputStream byteIn = new ByteArrayInputStream(tarGzBytes);
                GzipCompressorInputStream gzipIn = new GzipCompressorInputStream(byteIn);
                TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)
        ) {
            TarArchiveEntry entry;

            while ((entry = tarIn.getNextTarEntry()) != null) {
                // pomijamy katalogi
                if (entry.isDirectory()) {
                    continue;
                }

                String fileName = entry.getName();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                tarIn.transferTo(baos);
                String content = baos.toString(StandardCharsets.UTF_8);
                files.put(fileName, content);
            }

            return files;
        } catch (IOException e) {
            throw new SystemKSeFSDKException("Error while extracting tar.gz", e);
        }
    }

    private static void writeTarEntry(OutputStream tarStream, String fileName, byte[] content) throws IOException {
        byte[] header = new byte[TAR_BLOCK_SIZE];

        writeTarPath(header, fileName);

        writeTarOctal(header, 100, 8, 420); // 0644
        writeTarOctal(header, 108, 8, 0);   // uid
        writeTarOctal(header, 116, 8, 0);   // gid
        writeTarOctal(header, 124, 12, content.length);
        writeTarOctal(header, 136, 12, Instant.now().getEpochSecond());

        // checksum field najpierw spacje
        for (int i = 148; i < 156; i++) {
            header[i] = (byte) ' ';
        }

        header[156] = (byte) '0'; // regular file

        writeTarString(header, 257, 6, "ustar");
        writeTarString(header, 263, 2, "00");

        // checksum
        int checksum = 0;
        for (byte b : header) {
            checksum += (b & 0xFF);
        }

        writeTarChecksum(header, checksum);

        // zapis
        tarStream.write(header);
        tarStream.write(content);

        int paddingLength = (TAR_BLOCK_SIZE - (content.length % TAR_BLOCK_SIZE)) % TAR_BLOCK_SIZE;
        if (paddingLength > 0) {
            tarStream.write(new byte[paddingLength]);
        }
    }

    private static void writeTarPath(byte[] buffer, String path) {
        byte[] bytes = path.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bytes, 0, buffer, 0, Math.min(bytes.length, 100));
    }

    private static void writeTarString(byte[] buffer, int offset, int length, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bytes, 0, buffer, offset, Math.min(bytes.length, length));
    }

    private static void writeTarOctal(byte[] buffer, int offset, int length, long value) {
        String octal = Long.toOctalString(value);

        String padded = String.format("%" + (length - 1) + "s", octal).replace(' ', '0') + "\0";

        byte[] bytes = padded.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bytes, 0, buffer, offset, Math.min(bytes.length, length));
    }

    private static void writeTarChecksum(byte[] buffer, int checksum) {
        String octal = Integer.toOctalString(checksum);

        String padded = String.format("%6s", octal).replace(' ', '0');

        byte[] bytes = padded.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(bytes, 0, buffer, 148, bytes.length);

        buffer[154] = 0;              // null
        buffer[155] = (byte) ' ';     // space
    }
}

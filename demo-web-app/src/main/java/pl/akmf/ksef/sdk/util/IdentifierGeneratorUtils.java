package pl.akmf.ksef.sdk.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.YearMonth;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentifierGeneratorUtils {

    private static final String SHA_256 = "SHA-256";
    private static final Random random = new Random();

    public static String generateRandomNIP() {
        StringBuilder sb = new StringBuilder(10);

        sb.append("73");
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(0, 10));
        }

        return sb.toString();
    }

    public static String generatePeppolId() {
        StringBuilder sb = new StringBuilder(10);

        sb.append("PPL");
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(0, 10));
        }

        return sb.toString();
    }

    public static String generateRandomVatEu() {
        StringBuilder sb = new StringBuilder(10);
        sb.append("DE");
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(0, 10));
        }

        return sb.toString();
    }

    // --- NIP ---
    public static String getRandomNip() {
        return getRandomNip("73");
    }

    // Generuje losowy poprawny NIP (10 cyfr).
    // Wyrażenie regularne używane do walidacji:
    // [1-9]((\d[1-9])|([1-9]\d))\d{7}
    public static String getRandomNip(String prefixNumbers) {
        int first;
        int second;

        if (prefixNumbers.length() == 1) {
            first = Character.getNumericValue(prefixNumbers.charAt(0));
            if (first == 0) throw new IllegalArgumentException("Wartość musi być większa od 0");
            second = random.nextInt(10);
        } else if (prefixNumbers.length() == 2) {
            first = Character.getNumericValue(prefixNumbers.charAt(0));
            second = Character.getNumericValue(prefixNumbers.charAt(1));
        } else if (prefixNumbers.isEmpty()) {
            first = random.nextInt(9) + 1;
            second = random.nextInt(10);
        } else {
            throw new IllegalArgumentException("Prefiks musi mieć długość 0, 1 lub 2 cyfr.");
        }

        int third = random.nextInt(10);
        if (second == 0 && third == 0) {
            third = random.nextInt(9) + 1;
        }

        String prefix = "" + first + second + third;
        String rest = String.format("%07d", random.nextInt(10_000_000));
        return prefix + rest;
    }

    // --- PESEL ---
    // Generuje losowy poprawny PESEL (11 cyfr, z częścią daty w formacie RRMMDD).
    // Wyrażenie regularne używane do walidacji:
    // ^\d{2}(?:0[1-9]|1[0-2]|2[1-9]|3[0-2]|4[1-9]|5[0-2]|6[1-9]|7[0-2]|8[1-9]|9[0-2])\d{7}$
    public static String getRandomPesel() {
        int year = 1900 + random.nextInt(400); // 1900–2299
        int month = 1 + random.nextInt(12);
        YearMonth ym = YearMonth.of(year, month);
        int day = 1 + random.nextInt(ym.lengthOfMonth());

        int encodedMonth = month + (year / 100 - 19) * 20;
        String datePart = String.format("%02d%02d%02d", year % 100, encodedMonth, day);
        String serial = String.format("%04d", random.nextInt(10_000));

        String basePesel = datePart + serial;
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (basePesel.charAt(i) - '0') * weights[i];
        }
        int control = (10 - (sum % 10)) % 10;
        return basePesel + control;
    }

    // --- VAT UE ---
    // Generuje losowy poprawny identyfikator NIP-VAT UE.
    // Wyrażenie regularne używane do walidacji:
    // ^(?&lt;nip&gt;[1-9](\d[1-9]|[1-9]\d)\d{7})-(?&lt;vat&gt;((AT)(U\d{8})|(BE)([01]{1}\d{9})|(BG)(\d{9,10})|(CY)(\d{8}[A-Z])|(CZ)(\d{8,10})|(DE)(\d{9})|(DK)(\d{8})|(EE)(\d{9})|(EL)(\d{9})|(ES)([A-Z]\d{8}|\d{8}[A-Z]|[A-Z]\d{7}[A-Z])|(FI)(\d{8})|(FR)[A-Z0-9]{2}\d{9}|(HR)(\d{11})|(HU)(\d{8})|(IE)(\d{7}[A-Z]{2}|\d[A-Z0-9+*]\d{5}[A-Z])|(IT)(\d{11})|(LT)(\d{9}|\d{12})|(LU)(\d{8})|(LV)(\d{11})|(MT)(\d{8})|(NL)([A-Z0-9+*]{12})|(PT)(\d{9})|(RO)(\d{2,10})|(SE)(\d{12})|(SI)(\d{8})|(SK)(\d{10})|(XI)((\d{9}|(\d{12}))|(GD|HA)(\d{3}))))$
    public static String getRandomNipVatEU() {
        String nip = getRandomNip();
        return getRandomNipVatEU(nip);
    }

    public static String generateRandomPolishAccountNumber() {
        // Generate random 8-digit bank code
        StringBuilder bankCode = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            bankCode.append(random.nextInt(10));
        }

        return generatePolishAccountNumber(bankCode.toString());
    }

    public static String generatePolishAccountNumber(String bankCode) {
        if (bankCode == null || bankCode.length() != 8) {
            throw new IllegalArgumentException("Bank code must be 8 digits");
        }

        // Generate 16 random digits for account number
        StringBuilder accountPart = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            accountPart.append(random.nextInt(10));
        }

        // Calculate check digits for the account
        String accountWithoutCheck = bankCode + accountPart;
        String checkDigits = calculateAccountCheckDigits(accountWithoutCheck);

        return checkDigits + bankCode + accountPart;
    }

    private static String calculateAccountCheckDigits(String accountNumber) {
        int[] weights = {3, 9, 7, 1, 3, 9, 7, 1, 3, 9, 7, 1, 3, 9, 7, 1, 3, 9, 7, 1, 3, 9, 7, 1};

        int sum = 0;
        for (int i = 0; i < 24; i++) {
            int digit = Character.getNumericValue(accountNumber.charAt(i));
            sum += digit * weights[i];
        }

        int checksum = sum % 10;
        int checkDigit = (10 - checksum) % 10;

        return String.format("%02d", checkDigit);
    }

    public static String generateIban() {
        String accountNumber = generateRandomPolishAccountNumber();

        String checkDigits = calculateIbanCheckDigits(accountNumber);
        return "PL" + checkDigits + accountNumber;
    }

    private static String calculateIbanCheckDigits(String accountNumber) {
        // P=25, L=21, append 00 as placeholder
        String forCalculation = accountNumber + "252100";
        int checksum = 98 - mod97(forCalculation);
        return String.format("%02d", checksum);
    }

    private static int mod97(String number) {
        int remainder = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            remainder = (remainder * 10 + digit) % 97;
        }
        return remainder;
    }


    public static String getRandomNipVatEU(String nip) {
        return getRandomNipVatEU(nip, "ES");
    }

    public static String getRandomNipVatEU(String nip, String countryCode) {
        return nip + "-" + getRandomVatEU(countryCode);
    }

    // Generuje losowy, poprawny identyfikator VAT UE.
    public static String getRandomVatEU(String countryCode) {
        String vatPart;
        switch (countryCode) {
            case "AT" -> vatPart = "U" + getRandomDigits(8);
            case "BE" -> vatPart = random.nextInt(2) + getRandomDigits(9);
            case "BG" -> vatPart = random.nextBoolean()
                    ? getRandomDigits(9)
                    : String.valueOf(ThreadLocalRandom.current()
                    .nextLong(1_000_000_000L, 10_000_000_000L));
            case "CY" -> vatPart = getRandomDigits(8) + randomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            case "CZ" -> vatPart = random.nextBoolean()
                    ? getRandomDigits(8)
                    : String.valueOf(ThreadLocalRandom.current()
                    .nextLong(1_000_000_000L, 10_000_000_000L));
            case "DE" -> vatPart = getRandomDigits(9);
            case "DK" -> vatPart = getRandomDigits(8);
            case "EE" -> vatPart = getRandomDigits(9);
            case "EL" -> vatPart = getRandomDigits(9);
            case "ES" -> vatPart = generateEsVat();
            case "FI" -> vatPart = getRandomDigits(8);
            case "FR" -> vatPart = randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 2)
                    + getRandomDigits(9);
            case "HR" -> vatPart = String.valueOf(ThreadLocalRandom.current()
                    .nextLong(10_000_000_000L, 100_000_000_000L));
            case "HU" -> vatPart = getRandomDigits(8);
            case "IE" -> vatPart = generateIeVat();
            case "IT" -> vatPart = String.valueOf(getRandomLong(10_000_000_000L, 99_999_999_999L));
            case "LT" -> {
                if (random.nextBoolean()) {
                    vatPart = getRandomDigits(9);
                } else {
                    vatPart = String.valueOf(getRandomLong(100_000_000_000L, 999_999_999_999L));
                }
                return vatPart;
            }
            case "LU" -> vatPart = getRandomDigits(8);
            case "LV" -> vatPart = String.valueOf(getRandomLong(10_000_000_000L, 99_999_999_999L));
            case "MT" -> vatPart = getRandomDigits(8);
            case "NL" -> vatPart = randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+*", 12);
            case "PT" -> vatPart = getRandomDigits(9);
            case "RO" -> vatPart = String.valueOf(getRandomLong(10L, 9_999_999_999L));
            case "SE" -> vatPart = String.valueOf(getRandomLong(100_000_000_000L, 999_999_999_999L));
            case "SI" -> vatPart = getRandomDigits(8);
            case "SK" -> vatPart = String.valueOf(getRandomLong(1_000_000_000L, 9_999_999_999L));
            case "XI" -> vatPart = generateXiVat();

            default -> throw new IllegalArgumentException("Niewspierany kod kraju: " + countryCode);
        }

        return countryCode + vatPart;
    }

    public static String getNipVatEU(String nip, String vatue) {
        return nip + "-" + vatue;
    }

    private static String generateIeVat() {
        if (random.nextBoolean()) {
            // 7-cyfrowy numer + 2 litery
            return getRandomDigits(7) + randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 2);
        } else {
            // 1-cyfrowy numer + 1 znak alfanumeryczny + 5-cyfrowy numer + 1 litera
            String part1 = getRandomDigits(1);
            String part2 = randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+*", 1);
            String part3 = getRandomDigits(5);
            char lastChar = (char) ('A' + random.nextInt(26));
            return part1 + part2 + part3 + lastChar;
        }
    }

    private static String generateXiVat() {
        int choice = random.nextInt(3); // 0,1,2

        switch (choice) {
            case 0:
                // 9-cyfrowy numer
                return getRandomDigits(9);
            case 1:
                // 12-cyfrowy numer
                return getRandomDigits(12);
            default:
                // "GD" lub "HA" + 3-cyfrowy numer
                String prefix = random.nextBoolean() ? "GD" : "HA";
                return prefix + getRandomDigits(3);
        }
    }

    // --- helpers ---
    private static String getRandomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String randomString(String chars, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static char randomChar(String chars) {
        return chars.charAt(random.nextInt(chars.length()));
    }

    private static String generateEsVat() {
        int choice = random.nextInt(3);
        switch (choice) {
            case 0 -> {
                return randomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ") + getRandomDigits(8);
            }
            case 1 -> {
                return getRandomDigits(8) + randomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            }
            default -> {
                return randomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                        + getRandomDigits(7)
                        + randomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            }
        }
    }

    public static long getRandomLong(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        long bound = max - min + 1;
        long r;
        do {
            r = random.nextLong() & Long.MAX_VALUE; // zawsze >= 0
        } while (r >= bound * ((Long.MAX_VALUE / bound)));
        return min + (r % bound);
    }
}

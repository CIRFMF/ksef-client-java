package pl.akmf.ksef.sdk.client.model.util;

import java.util.regex.Pattern;

public final class RegexPatterns {

    private RegexPatterns() {
    }

    public static abstract class Regex {
        private final Pattern pattern;

        protected Regex(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public boolean isMatch(String value) {
            if (value == null) return false;
            return pattern.matcher(value).matches();
        }
    }

    public static final Regex NipPatternCore = new Regex("[1-9]((\\d[1-9])|([1-9]\\d))\\d{7}") {
    };
    public static final Regex VatUePatternCore = new Regex("(ATU\\d{8}|BE[01]{1}\\d{9}|BG\\d{9,10}|CY\\d{8}[A-Z]|CZ\\d{8,10}|DE\\d{9}|DK\\d{8}|EE\\d{9}|EL\\d{9}|ES([A-Z]\\d{8}|\\d{8}[A-Z]|[A-Z]\\d{7}[A-Z])|FI\\d{8}|FR[A-Z0-9]{2}\\d{9}|HR\\d{11}|HU\\d{8}|IE(\\d{7}[A-Z]{2}|\\d[A-Z0-9+*]\\d{5}[A-Z])|IT\\d{11}|LT(\\d{9}|\\d{12})|LU\\d{8}|LV\\d{11}|MT\\d{8}|NL[A-Z0-9+*]{12}|PT\\d{9}|RO\\d{2,10}|SE\\d{12}|SI\\d{8}|SK\\d{10}|XI((\\d{9}|\\d{12})|(GD|HA)\\d{3}))") {
    };
    public static final Regex InternalIdPattern = new Regex("^[1-9]((\\d[1-9])|([1-9]\\d))\\d{7}-\\d{5}$") {
    };
    public static final Regex PeppolIdPattern = new Regex("^P[A-Z]{2}[0-9]{6}$") {
    };
    public static final Regex ReferenceNumberPattern = new Regex("^(20[2-9][0-9]|2[1-9][0-9]{2}|[3-9][0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-([0-9A-Z]{2})-([0-9A-F]{10})-([0-9A-F]{10})-([0-9A-F]{2})$") {
    };
    public static final Regex KsefNumberPattern = new Regex("^([1-9](\\d[1-9]|[1-9]\\d)\\d{7})-(20[2-9][0-9]|2[1-9]\\d{2}|[3-9]\\d{3})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])-([0-9A-F]{6})-?([0-9A-F]{6})-([0-9A-F]{2})$") {
    };
    public static final Regex CertificateNamePattern = new Regex("^[a-zA-Z0-9_\\-\\ ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$") {
    };
    public static final Regex PeselPattern = new Regex("^\\d{2}(?:0[1-9]|1[0-2]|2[1-9]|3[0-2]|4[1-9]|5[0-2]|6[1-9]|7[0-2]|8[1-9]|9[0-2])\\d{7}$") {
    };
    public static final Regex CertificateFingerPrintSha256Pattern = new Regex("^[0-9A-F]{64}$") {
    };
    public static final Regex KsefNumberV36Pattern = new Regex("^([1-9]((\\d[1-9])|([1-9]\\d))\\d{7}|M\\d{9}|[A-Z]{3}\\d{7})-(20[2-9][0-9]|2[1-9][0-9]{2}|[3-9][0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-([0-9A-F]{6})-([0-9A-F]{6})-([0-9A-F]{2})$") {
    };
    public static final Regex KsefNumberV35Pattern = new Regex("^([1-9]((\\d[1-9])|([1-9]\\d))\\d{7}|M\\d{9}|[A-Z]{3}\\d{7})-(20[2-9][0-9]|2[1-9][0-9]{2}|[3-9][0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])-([0-9A-F]{6})([0-9A-F]{6})-([0-9A-F]{2})$") {
    };
    public static final Regex Base64Pattern = new Regex("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$") {
    };
    public static final Regex Ip4AddressPattern = new Regex("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$") {
    };
    public static final Regex Ip4RangePattern = new Regex("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}-((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$") {
    };
    public static final Regex Ip4MaskPattern = new Regex("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\\/(0|[1-9]|1[0-9]|2[0-9]|3[0-2])$") {
    };
    public static final Regex Sha256Base64Pattern = new Regex("^[A-Za-z0-9+/]{43}=$") {
    };
}

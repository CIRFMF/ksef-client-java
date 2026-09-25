package pl.akmf.ksef.sdk.client.model.auth;

import java.util.List;

/**
 * AuthKsefTokenRequest.
 * <p>
 * Odpowiednik w specyfikacji OpenAPI KSeF API 2.0: {@code InitTokenAuthenticationRequest}.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class AuthKsefTokenRequest {

    /**
     * Wygenerowany wcześniej challenge.
     */
    private String challenge;

    /**
     * Identyfikator kontekstu do którego następuje uwierzytelnienie.
     */
    private ContextIdentifier contextIdentifier;

    /**
     * Zaszyfrowany token wraz z timestampem z challenge'a, w postaci {@code token|timestamp}, zakodowany w formacie Base64.
     */
    private String encryptedToken;

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania tokena.
     * (skrót SHA-256 z DER, zakodowany w Base64).
     * Pobierany z defaultCryptographyService.getKsefToken().getPublicKeyId()
     */
    private String publicKeyId;

    @Deprecated
    private IpAddressPolicy ipAddressPolicy;

    private AuthorizationPolicy authorizationPolicy;

    public AuthKsefTokenRequest() {
    }

    public AuthKsefTokenRequest(String challenge, ContextIdentifier contextIdentifier, String encryptedToken, IpAddressPolicy ipAddressPolicy) {
        this.challenge = challenge;
        this.contextIdentifier = contextIdentifier;
        this.encryptedToken = encryptedToken;
        this.ipAddressPolicy = ipAddressPolicy;
    }

    public AuthKsefTokenRequest(String challenge, ContextIdentifier contextIdentifier, String encryptedToken, String publicKeyId, IpAddressPolicy ipAddressPolicy) {
        this.challenge = challenge;
        this.contextIdentifier = contextIdentifier;
        this.encryptedToken = encryptedToken;
        this.publicKeyId = publicKeyId;
        this.ipAddressPolicy = ipAddressPolicy;
    }

    public AuthKsefTokenRequest(String challenge, ContextIdentifier contextIdentifier, String encryptedToken, String publicKeyId, AuthorizationPolicy authorizationPolicy) {
        this.challenge = challenge;
        this.contextIdentifier = contextIdentifier;
        this.encryptedToken = encryptedToken;
        this.publicKeyId = publicKeyId;
        this.authorizationPolicy = authorizationPolicy;
    }

    /**
     * Wygenerowany wcześniej challenge.
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * Wygenerowany wcześniej challenge.
     */
    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    /**
     * Identyfikator kontekstu do którego następuje uwierzytelnienie.
     */
    public ContextIdentifier getContextIdentifier() {
        return contextIdentifier;
    }

    /**
     * Identyfikator kontekstu do którego następuje uwierzytelnienie.
     */
    public void setContextIdentifier(ContextIdentifier contextIdentifier) {
        this.contextIdentifier = contextIdentifier;
    }

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania tokena.
     * (skrót SHA-256 z DER, zakodowany w Base64).
     * Pobierany z defaultCryptographyService.getKsefToken().getPublicKeyId()
     */
    public String getPublicKeyId() {
        return publicKeyId;
    }

    /**
     * Identyfikator klucza publicznego użytego do szyfrowania tokena.
     * (skrót SHA-256 z DER, zakodowany w Base64).
     * Pobierany z defaultCryptographyService.getKsefToken().getPublicKeyId()
     */
    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }

    /**
     * Zaszyfrowany token wraz z timestampem z challenge'a, w postaci {@code token|timestamp}, zakodowany w formacie Base64.
     */
    public String getEncryptedToken() {
        return encryptedToken;
    }

    /**
     * Zaszyfrowany token wraz z timestampem z challenge'a, w postaci {@code token|timestamp}, zakodowany w formacie Base64.
     */
    public void setEncryptedToken(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }

    public IpAddressPolicy getIpAddressPolicy() {
        return ipAddressPolicy;
    }

    public void setIpAddressPolicy(IpAddressPolicy ipAddressPolicy) {
        this.ipAddressPolicy = ipAddressPolicy;
    }

    public AuthorizationPolicy getAuthorizationPolicy() {
        return authorizationPolicy;
    }

    public void setAuthorizationPolicy(AuthorizationPolicy authorizationPolicy) {
        this.authorizationPolicy = authorizationPolicy;
    }

    /**
     * AuthorizationPolicy.
     *
     * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
     */
    public static class AuthorizationPolicy {

        /**
         * Lista dozwolonych adresów IP.
         */
        private AllowedIps allowedIps;

        public AuthorizationPolicy() {
        }

        public AuthorizationPolicy(AllowedIps allowedIps) {
            this.allowedIps = allowedIps;
        }

        /**
         * Lista dozwolonych adresów IP.
         */
        public AllowedIps getAllowedIps() {
            return allowedIps;
        }

        /**
         * Lista dozwolonych adresów IP.
         */
        public void setAllowedIps(AllowedIps allowedIps) {
            this.allowedIps = allowedIps;
        }

        /**
         * AllowedIps.
         *
         * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
         */
        class AllowedIps {

            /**
             * Lista adresów IPv4 w notacji dziesiętnej kropkowanej, np. `192.168.0.10`.
             */
            private List<String> ip4Addresses;

            /**
             * Lista adresów IPv4 podana w formie zakresu początek–koniec, oddzielonego pojedynczym myślnikiem, np. `10.0.0.1–10.0.0.254`.
             */
            private List<String> ip4Ranges;

            /**
             * Lista adresów IPv4 w notacji CIDR, np. `172.16.0.0/16`.
             */
            private List<String> ip4Masks;

            public AllowedIps() {
            }

            public AllowedIps(List<String> ip4Addresses, List<String> ip4Ranges, List<String> ip4Masks) {
                this.ip4Addresses = ip4Addresses;
                this.ip4Ranges = ip4Ranges;
                this.ip4Masks = ip4Masks;
            }

            /**
             * Lista adresów IPv4 w notacji dziesiętnej kropkowanej, np. `192.168.0.10`.
             */
            public List<String> getIp4Addresses() {
                return ip4Addresses;
            }

            /**
             * Lista adresów IPv4 w notacji dziesiętnej kropkowanej, np. `192.168.0.10`.
             */
            public void setIp4Addresses(List<String> ip4Addresses) {
                this.ip4Addresses = ip4Addresses;
            }

            /**
             * Lista adresów IPv4 podana w formie zakresu początek–koniec, oddzielonego pojedynczym myślnikiem, np. `10.0.0.1–10.0.0.254`.
             */
            public List<String> getIp4Ranges() {
                return ip4Ranges;
            }

            /**
             * Lista adresów IPv4 podana w formie zakresu początek–koniec, oddzielonego pojedynczym myślnikiem, np. `10.0.0.1–10.0.0.254`.
             */
            public void setIp4Ranges(List<String> ip4Ranges) {
                this.ip4Ranges = ip4Ranges;
            }

            /**
             * Lista adresów IPv4 w notacji CIDR, np. `172.16.0.0/16`.
             */
            public List<String> getIp4Masks() {
                return ip4Masks;
            }

            /**
             * Lista adresów IPv4 w notacji CIDR, np. `172.16.0.0/16`.
             */
            public void setIp4Masks(List<String> ip4Masks) {
                this.ip4Masks = ip4Masks;
            }
        }
    }

    public static class IpAddressPolicy {

        private IpChangePolicyEnum onClientIpChange;

        private AllowedIps allowedIps;

        public IpAddressPolicy(IpChangePolicyEnum onClientIpChange, AllowedIps allowedIps) {
            this.onClientIpChange = onClientIpChange;
            this.allowedIps = allowedIps;
        }

        public IpAddressPolicy() {
        }

        public IpChangePolicyEnum getOnClientIpChange() {
            return onClientIpChange;
        }

        public void setOnClientIpChange(IpChangePolicyEnum onClientIpChange) {
            this.onClientIpChange = onClientIpChange;
        }

        public AllowedIps getAllowedIps() {
            return allowedIps;
        }

        public void setAllowedIps(AllowedIps allowedIps) {
            this.allowedIps = allowedIps;
        }
    }

    public enum IpChangePolicyEnum {

        IGNORE("ignore"), REJECT("reject");

        private final String value;

        IpChangePolicyEnum(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static IpChangePolicyEnum fromValue(String v) {
            for (IpChangePolicyEnum c : IpChangePolicyEnum.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    public static class AllowedIps {

        private List<String> ipAddress;

        private List<String> ipRange;

        private List<String> ipMask;

        public AllowedIps(List<String> ipAddress, List<String> ipRange, List<String> ipMask) {
            this.ipAddress = ipAddress;
            this.ipRange = ipRange;
            this.ipMask = ipMask;
        }

        public AllowedIps() {
        }

        public List<String> getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(List<String> ipAddress) {
            this.ipAddress = ipAddress;
        }

        public List<String> getIpRange() {
            return ipRange;
        }

        public void setIpRange(List<String> ipRange) {
            this.ipRange = ipRange;
        }

        public List<String> getIpMask() {
            return ipMask;
        }

        public void setIpMask(List<String> ipMask) {
            this.ipMask = ipMask;
        }
    }
}

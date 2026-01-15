package pl.akmf.ksef.sdk.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.akmf.ksef.sdk.api.KsefApiProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "sdk.config")
public class ExampleApiProperties extends KsefApiProperties {

    private String baseUri;
    private String suffixUri;
    private String qrUri;
    private int requestTimeout;
    private Map<String, String> defaultHeaders;

    @Override
    public String getBaseUri() {
        return baseUri;
    }

    @Override
    public String getSuffixUri() {
        return suffixUri;
    }

    @Override
    public String getQrUri() {
        return qrUri;
    }

    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(requestTimeout);
    }

    @Override
    public Map<String, String> getDefaultHeaders() {
        if (defaultHeaders == null) {
            defaultHeaders = new HashMap<>();
        }
        return defaultHeaders;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void setSuffixUri(String suffixUri) {
        this.suffixUri = suffixUri;
    }

    public void setQrUri(String qrUri) {
        this.qrUri = qrUri;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }
}

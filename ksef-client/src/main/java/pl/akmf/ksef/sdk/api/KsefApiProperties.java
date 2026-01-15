package pl.akmf.ksef.sdk.api;

import java.time.Duration;
import java.util.Map;

public abstract class KsefApiProperties {

    public abstract String getBaseUri();
    public abstract String getSuffixUri();
    public abstract String getQrUri();

    public abstract Duration getRequestTimeout();

    public abstract Map<String, String> getDefaultHeaders();
}

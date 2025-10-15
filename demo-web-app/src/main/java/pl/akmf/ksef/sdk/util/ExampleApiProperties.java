package pl.akmf.ksef.sdk.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import pl.akmf.ksef.sdk.api.KsefApiProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ExampleApiProperties extends KsefApiProperties {

    @Getter
    @Value("${ksef.base-uri}")
    private String baseUri;

    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(5);
    }

    @Override
    public Map<String, String> getDefaultHeaders() {

        return new HashMap<>();
    }
}

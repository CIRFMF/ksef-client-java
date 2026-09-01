package pl.akmf.ksef.sdk.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObservationOptions;
import pl.akmf.ksef.sdk.system.headerobservation.ResponseHeaderObservationProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "sdk.config.response-header-observation")
public class ExampleResponseHeaderObservationProperties extends ResponseHeaderObservationProperties {

    private boolean enabled = false;
    private Set<String> headerNames = new HashSet<>(Collections.singleton("X-System-Warning"));

    private ResponseHeaderObservationOptions responseHeaderObservationOptions;

    @Override
    public ResponseHeaderObservationOptions getResponseHeaderObservationOptions() {
        if (responseHeaderObservationOptions == null) {
            responseHeaderObservationOptions = new ResponseHeaderObservationOptions(enabled, headerNames);
        }
        return responseHeaderObservationOptions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(Set<String> headerNames) {
        this.headerNames = headerNames;
    }
}

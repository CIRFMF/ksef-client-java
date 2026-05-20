package pl.akmf.ksef.sdk.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerOptions;
import pl.akmf.ksef.sdk.system.circuitbreaker.KsefCircuitBreakerProperties;

@Configuration
@ConfigurationProperties(prefix = "sdk.config.circuit-breaker-options")
public class ExampleCircuitBreakerProperties extends KsefCircuitBreakerProperties {

    private boolean enabled = true;
    private int failureThreshold = 5;
    private int breakDurationSeconds = 30;

    private KsefCircuitBreakerOptions ksefCircuitBreakerOptions;

    @Override
    public KsefCircuitBreakerOptions getCircuitBreakerOptions() {
        if (ksefCircuitBreakerOptions == null) {
            ksefCircuitBreakerOptions = new KsefCircuitBreakerOptions(enabled, failureThreshold, breakDurationSeconds);
        }
        return ksefCircuitBreakerOptions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getFailureThreshold() {
        return failureThreshold;
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }

    public int getBreakDurationSeconds() {
        return breakDurationSeconds;
    }

    public void setBreakDurationSeconds(int breakDurationSeconds) {
        this.breakDurationSeconds = breakDurationSeconds;
    }
}
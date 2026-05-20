package pl.akmf.ksef.sdk.system.circuitbreaker;

// Konfiguracja Circuit Breakera dla wywołań HTTP do KSeF.
public class KsefCircuitBreakerOptions {

    // Włącza lub wyłącza mechanizm Circuit Breakera.
    private boolean enabled = true;
    // Liczba kolejnych błędów przejściowych, po której obwód zostanie otwarty.
    private int failureThreshold = 5;
    // Czas (w sekundach), przez jaki obwód pozostaje otwarty.
    private int breakDurationSeconds = 30;

    public KsefCircuitBreakerOptions() {
    }

    public KsefCircuitBreakerOptions(boolean enabled, int failureThreshold, int breakDurationSeconds) {
        this.enabled = enabled;
        this.failureThreshold = failureThreshold;
        this.breakDurationSeconds = breakDurationSeconds;
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

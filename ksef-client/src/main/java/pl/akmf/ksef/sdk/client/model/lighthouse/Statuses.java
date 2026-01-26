package pl.akmf.ksef.sdk.client.model.lighthouse;

// Statusy systemu KSeF zwracane przez Latarnię.
public class Statuses {

    // Kod 0 — pełna dostępność.
    public static final int fullAvailability = 0;

    // Kod 100 — trwająca niedostępność.
    public static final int ongoingUnavailability = 100;

    // Kod 500 — trwająca awaria.
    public static final int ongoingFailure = 500;

    // Kod 900 — trwająca awaria całkowita.
    public static final int ongoingTotalFailure = 900;
}

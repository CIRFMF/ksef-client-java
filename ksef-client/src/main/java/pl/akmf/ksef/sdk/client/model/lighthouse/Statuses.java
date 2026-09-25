package pl.akmf.ksef.sdk.client.model.lighthouse;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Kod statusu systemu KSeF zwracany przez usługę statusową ("Latarnia" / Lighthouse) —
 * odrębną usługę monitorującą dostępność systemu KSeF
 */
public enum Statuses {

    FULL_AVAILABILITY(0), ONGOING_UNAVAILABILITY(100), ONGOING_FAILURE(500), ONGOING_TOTAL_FAILURE(900);

    private final int value;

    Statuses(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

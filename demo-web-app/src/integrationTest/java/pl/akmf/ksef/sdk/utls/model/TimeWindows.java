package pl.akmf.ksef.sdk.utls.model;

import java.time.OffsetDateTime;

public class TimeWindows {
    private final OffsetDateTime from;
    private final OffsetDateTime to;

    public TimeWindows(OffsetDateTime from, OffsetDateTime to) {
        this.from = from;
        this.to = to;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public OffsetDateTime getTo() {
        return to;
    }
}

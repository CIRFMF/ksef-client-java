package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.OffsetDateTime;

public class InvoiceQueryDateRange {
    private InvoiceQueryDateType dateType;

    private OffsetDateTime from;

    private OffsetDateTime to;

    public InvoiceQueryDateRange() {
    }

    public InvoiceQueryDateRange(InvoiceQueryDateType dateType, OffsetDateTime from, OffsetDateTime to) {
        this.dateType = dateType;
        this.from = from;
        this.to = to;
    }

    public InvoiceQueryDateType getDateType() {
        return dateType;
    }

    public void setDateType(InvoiceQueryDateType dateType) {
        this.dateType = dateType;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public void setFrom(OffsetDateTime from) {
        this.from = from;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public void setTo(OffsetDateTime to) {
        this.to = to;
    }
}


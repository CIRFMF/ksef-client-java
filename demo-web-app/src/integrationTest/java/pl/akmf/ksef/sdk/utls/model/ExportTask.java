package pl.akmf.ksef.sdk.utls.model;

import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;

import java.time.OffsetDateTime;

public class ExportTask {
    private final OffsetDateTime from;
    private final OffsetDateTime to;
    private final InvoiceQuerySubjectType subjectType;

    public ExportTask(OffsetDateTime from, OffsetDateTime to, InvoiceQuerySubjectType subjectType) {
        this.from = from;
        this.to = to;
        this.subjectType = subjectType;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public InvoiceQuerySubjectType getSubjectType() {
        return subjectType;
    }
}

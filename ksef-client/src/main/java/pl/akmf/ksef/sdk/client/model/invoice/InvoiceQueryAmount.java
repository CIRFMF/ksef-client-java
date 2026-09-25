package pl.akmf.ksef.sdk.client.model.invoice;

/**
 * InvoiceQueryAmount.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class InvoiceQueryAmount {

    private AmountType type;

    private Double from;

    private Double to;

    public InvoiceQueryAmount() {
    }

    public AmountType getType() {
        return type;
    }

    public void setType(AmountType type) {
        this.type = type;
    }

    public Double getFrom() {
        return from;
    }

    public void setFrom(Double from) {
        this.from = from;
    }

    public Double getTo() {
        return to;
    }

    public void setTo(Double to) {
        this.to = to;
    }
}

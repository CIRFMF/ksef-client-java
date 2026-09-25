package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.math.BigDecimal;

/**
 * CollectiveIdentifierInvoicesQueryResponseItemPayment.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoicesQueryResponseItemPayment {

    /**
     * Kwota płatności za fakturę.
     */
    private BigDecimal amount;

    /**
     * Kod waluty.
     */
    private String currency;

    public CollectiveIdentifierInvoicesQueryResponseItemPayment() {
    }

    public CollectiveIdentifierInvoicesQueryResponseItemPayment(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Kwota płatności za fakturę.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Kwota płatności za fakturę.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Kod waluty.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Kod waluty.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

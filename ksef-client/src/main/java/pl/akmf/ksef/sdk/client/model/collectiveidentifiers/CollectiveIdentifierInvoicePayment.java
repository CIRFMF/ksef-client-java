package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import pl.akmf.ksef.sdk.client.model.invoice.CurrencyCode;
import java.math.BigDecimal;

/**
 * CollectiveIdentifierInvoicePayment.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoicePayment {

    /**
     * Kwota płatności za fakturę.
     */
    private BigDecimal amount;

    /**
     * Kod waluty.
     */
    private CurrencyCode currency;

    public CollectiveIdentifierInvoicePayment() {
    }

    public CollectiveIdentifierInvoicePayment(BigDecimal amount, CurrencyCode currency) {
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
    public CurrencyCode getCurrency() {
        return currency;
    }

    /**
     * Kod waluty.
     */
    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }
}

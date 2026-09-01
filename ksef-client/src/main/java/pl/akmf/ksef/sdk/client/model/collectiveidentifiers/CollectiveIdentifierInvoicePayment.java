package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import pl.akmf.ksef.sdk.client.model.invoice.CurrencyCode;

import java.math.BigDecimal;

public class CollectiveIdentifierInvoicePayment {

    private BigDecimal amount;
    private CurrencyCode currency;

    public CollectiveIdentifierInvoicePayment() {
    }

    public CollectiveIdentifierInvoicePayment(BigDecimal amount, CurrencyCode currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }
}

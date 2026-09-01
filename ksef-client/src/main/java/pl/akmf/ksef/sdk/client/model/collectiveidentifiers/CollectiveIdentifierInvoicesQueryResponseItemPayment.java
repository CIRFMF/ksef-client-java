package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

import java.math.BigDecimal;

public class CollectiveIdentifierInvoicesQueryResponseItemPayment {

    private BigDecimal amount;
    private String currency;

    public CollectiveIdentifierInvoicesQueryResponseItemPayment() {
    }

    public CollectiveIdentifierInvoicesQueryResponseItemPayment(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

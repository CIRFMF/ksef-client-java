package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

/**
 * CollectiveIdentifierInvoice.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoice {

    /**
     * Numer ksef faktury.
     * <p>
     * Jedna faktura może zostać przypisana do maksymalnie 132 identyfikatorów zbiorczych w ramach jednego kontekstu.
     */
    private String ksefNumber;

    /**
     * Dane o płatności za fakturę
     */
    private CollectiveIdentifierInvoicePayment payment;

    /**
     * Opis
     */
    private String description;

    public CollectiveIdentifierInvoice() {
    }

    public CollectiveIdentifierInvoice(String ksefNumber, CollectiveIdentifierInvoicePayment payment, String description) {
        this.ksefNumber = ksefNumber;
        this.payment = payment;
        this.description = description;
    }

    /**
     * Numer ksef faktury.
     * <p>
     * Jedna faktura może zostać przypisana do maksymalnie 132 identyfikatorów zbiorczych w ramach jednego kontekstu.
     */
    public String getKsefNumber() {
        return ksefNumber;
    }

    /**
     * Numer ksef faktury.
     * <p>
     * Jedna faktura może zostać przypisana do maksymalnie 132 identyfikatorów zbiorczych w ramach jednego kontekstu.
     */
    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    /**
     * Dane o płatności za fakturę
     */
    public CollectiveIdentifierInvoicePayment getPayment() {
        return payment;
    }

    /**
     * Dane o płatności za fakturę
     */
    public void setPayment(CollectiveIdentifierInvoicePayment payment) {
        this.payment = payment;
    }

    /**
     * Opis
     */
    public String getDescription() {
        return description;
    }

    /**
     * Opis
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

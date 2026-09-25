package pl.akmf.ksef.sdk.client.model.collectiveidentifiers;

/**
 * CollectiveIdentifierInvoicesQueryResponseItem.
 *
 * @see <a href="https://api.ksef.mf.gov.pl/docs/v2/openapi.json">Specyfikacja OpenAPI KSeF API 2.0</a>
 */
public class CollectiveIdentifierInvoicesQueryResponseItem {

    /**
     * Numer ksef faktury.
     */
    private String ksefNumber;

    /**
     * Dane o płatności za fakturę
     */
    private CollectiveIdentifierInvoicesQueryResponseItemPayment payment;

    /**
     * Opis
     */
    private String description;

    /**
     * Określa czy informacje o szczegółach zostały ukryte z powodu braku dostępu do faktury.
     */
    private Boolean detailsHidden;

    /**
     * Numer identyfikatora zbiorczego.
     */
    private String collectiveIdentifierNumber;

    public CollectiveIdentifierInvoicesQueryResponseItem() {
    }

    public CollectiveIdentifierInvoicesQueryResponseItem(String ksefNumber, CollectiveIdentifierInvoicesQueryResponseItemPayment payment, String description, Boolean detailsHidden, String collectiveIdentifierNumber) {
        this.ksefNumber = ksefNumber;
        this.payment = payment;
        this.description = description;
        this.detailsHidden = detailsHidden;
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }

    /**
     * Numer ksef faktury.
     */
    public String getKsefNumber() {
        return ksefNumber;
    }

    /**
     * Numer ksef faktury.
     */
    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    /**
     * Dane o płatności za fakturę
     */
    public CollectiveIdentifierInvoicesQueryResponseItemPayment getPayment() {
        return payment;
    }

    /**
     * Dane o płatności za fakturę
     */
    public void setPayment(CollectiveIdentifierInvoicesQueryResponseItemPayment payment) {
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

    /**
     * Określa czy informacje o szczegółach zostały ukryte z powodu braku dostępu do faktury.
     */
    public Boolean getDetailsHidden() {
        return detailsHidden;
    }

    /**
     * Określa czy informacje o szczegółach zostały ukryte z powodu braku dostępu do faktury.
     */
    public void setDetailsHidden(Boolean detailsHidden) {
        this.detailsHidden = detailsHidden;
    }

    /**
     * Numer identyfikatora zbiorczego.
     */
    public String getCollectiveIdentifierNumber() {
        return collectiveIdentifierNumber;
    }

    /**
     * Numer identyfikatora zbiorczego.
     */
    public void setCollectiveIdentifierNumber(String collectiveIdentifierNumber) {
        this.collectiveIdentifierNumber = collectiveIdentifierNumber;
    }
}

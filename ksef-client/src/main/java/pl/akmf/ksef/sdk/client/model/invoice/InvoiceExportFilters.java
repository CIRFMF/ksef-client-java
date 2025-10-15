package pl.akmf.ksef.sdk.client.model.invoice;

import java.util.List;

public class InvoiceExportFilters {
    private InvoiceQuerySubjectType subjectType;
    private InvoiceQueryDateRange dateRange;
    private String ksefNumber;
    private String invoiceNumber;
    private InvoiceQueryAmount amount;
    private String sellerNip;
    private InvoiceBuyerIdentifier buyerIdentifier;
    private List<CurrencyCode> currencyCodes;
    private InvoicingMode invoicingMode;
    private Boolean isSelfInvoicing;
    private InvoiceFormType formType;
    private List<InvoiceMetadataInvoiceType> invoiceTypes;
    private Boolean hasAttachment;

    public InvoiceExportFilters() {

    }

    public InvoiceQuerySubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(InvoiceQuerySubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public InvoiceQueryDateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(InvoiceQueryDateRange dateRange) {
        this.dateRange = dateRange;
    }

    public String getKsefNumber() {
        return ksefNumber;
    }

    public void setKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceQueryAmount getAmount() {
        return amount;
    }

    public void setAmount(InvoiceQueryAmount amount) {
        this.amount = amount;
    }

    public String getSellerNip() {
        return sellerNip;
    }

    public void setSellerNip(String sellerNip) {
        this.sellerNip = sellerNip;
    }

    public InvoiceBuyerIdentifier getBuyerIdentifier() {
        return buyerIdentifier;
    }

    public void setBuyerIdentifier(InvoiceBuyerIdentifier buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
    }

    public List<CurrencyCode> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(List<CurrencyCode> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public InvoicingMode getInvoicingMode() {
        return invoicingMode;
    }

    public void setInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
    }

    public Boolean getIsSelfInvoicing() {
        return isSelfInvoicing;
    }

    public void setIsSelfInvoicing(Boolean isSelfInvoicing) {
        this.isSelfInvoicing = isSelfInvoicing;
    }

    public InvoiceFormType getFormType() {
        return formType;
    }

    public void setFormType(InvoiceFormType formType) {
        this.formType = formType;
    }

    public List<InvoiceMetadataInvoiceType> getInvoiceTypes() {
        return invoiceTypes;
    }

    public void setInvoiceTypes(List<InvoiceMetadataInvoiceType> invoiceTypes) {
        this.invoiceTypes = invoiceTypes;
    }

    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
}

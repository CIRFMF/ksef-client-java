package pl.akmf.ksef.sdk.api.builders.invoices;

import pl.akmf.ksef.sdk.client.model.invoice.CurrencyCode;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceFormType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadataInvoiceType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryAmount;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceBuyerIdentifier;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicingMode;

import java.util.List;

public class InvoiceQueryFiltersBuilder {
    private InvoiceQuerySubjectType subjectType;
    private InvoiceQueryDateRange dateRange;
    private String ksefNumber;
    private String invoiceNumber;
    private InvoiceQueryAmount amount;
    private String sellerNip;
    private InvoiceBuyerIdentifier buyerIdentifier;
    private List<CurrencyCode> currencyCodes;
    private Boolean hasAttachment;
    private InvoicingMode invoicingMode;
    private Boolean isSelfInvoicing;
    private InvoiceFormType formType;
    private List<InvoiceMetadataInvoiceType> invoiceTypes;

    public InvoiceQueryFiltersBuilder withSubjectType(InvoiceQuerySubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public InvoiceQueryFiltersBuilder withDateRange(InvoiceQueryDateRange dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public InvoiceQueryFiltersBuilder withKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
        return this;
    }

    public InvoiceQueryFiltersBuilder withInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public InvoiceQueryFiltersBuilder withAmount(InvoiceQueryAmount amount) {
        this.amount = amount;
        return this;
    }

    public InvoiceQueryFiltersBuilder withSellerNip(String sellerNip) {
        this.sellerNip = sellerNip;
        return this;
    }

    public InvoiceQueryFiltersBuilder withBuyerIdentifier(InvoiceBuyerIdentifier buyerIdentifier) {
        this.buyerIdentifier = buyerIdentifier;
        return this;
    }

    public InvoiceQueryFiltersBuilder withCurrencyCodes(List<CurrencyCode> currencyCodes) {
        this.currencyCodes = currencyCodes;
        return this;
    }

    public InvoiceQueryFiltersBuilder withHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
        return this;
    }

    public InvoiceQueryFiltersBuilder withInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
        return this;
    }

    public InvoiceQueryFiltersBuilder withIsSelfInvoicing(Boolean isSelfInvoicing) {
        this.isSelfInvoicing = isSelfInvoicing;
        return this;
    }

    public InvoiceQueryFiltersBuilder withFormType(InvoiceFormType formType) {
        this.formType = formType;
        return this;
    }

    public InvoiceQueryFiltersBuilder withInvoiceTypes(List<InvoiceMetadataInvoiceType> invoiceTypes) {
        this.invoiceTypes = invoiceTypes;
        return this;
    }

    public InvoiceQueryFilters build() {
        InvoiceQueryFilters request = new InvoiceQueryFilters();
        request.setSubjectType(subjectType);
        request.setDateRange(dateRange);
        request.setKsefNumber(ksefNumber);
        request.setInvoiceNumber(invoiceNumber);
        request.setAmount(amount);
        request.setSellerNip(sellerNip);
        request.setBuyerIdentifier(buyerIdentifier);
        request.setCurrencyCodes(currencyCodes);
        request.setInvoicingMode(invoicingMode);
        request.setSelfInvoicing(isSelfInvoicing);
        request.setFormType(formType);
        request.setInvoiceTypes(invoiceTypes);
        request.setHasAttachment(hasAttachment);
        return request;
    }
}


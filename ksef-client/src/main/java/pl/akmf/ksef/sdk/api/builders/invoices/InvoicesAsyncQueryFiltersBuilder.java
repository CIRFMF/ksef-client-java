package pl.akmf.ksef.sdk.api.builders.invoices;

import pl.akmf.ksef.sdk.client.model.invoice.CurrencyCode;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceMetadataInvoiceType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceFormType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryAmount;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceBuyerIdentifier;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryDateRange;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQuerySubjectType;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportFilters;
import pl.akmf.ksef.sdk.client.model.invoice.InvoicingMode;

import java.util.List;

public class InvoicesAsyncQueryFiltersBuilder {
    private InvoiceQuerySubjectType subjectType;
    private InvoiceQueryDateRange dateRange;
    private String ksefNumber;
    private String invoiceNumber;
    private InvoiceQueryAmount amount;
    private String sellerNip;
    private InvoiceBuyerIdentifier buyer;
    private List<CurrencyCode> currencyCodes;
    private InvoicingMode invoicingMode;
    private Boolean isSelfInvoicing;
    private InvoiceFormType formType;
    private List<InvoiceMetadataInvoiceType> invoiceTypes;
    private Boolean hasAttachment;

    public InvoicesAsyncQueryFiltersBuilder withSubjectType(InvoiceQuerySubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withDateRange(InvoiceQueryDateRange dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withKsefNumber(String ksefNumber) {
        this.ksefNumber = ksefNumber;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withAmount(InvoiceQueryAmount amount) {
        this.amount = amount;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withSellerNip(String sellerNip) {
        this.sellerNip = sellerNip;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withBuyer(InvoiceBuyerIdentifier buyer) {
        this.buyer = buyer;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withCurrencyCodes(List<CurrencyCode> currencyCodes) {
        this.currencyCodes = currencyCodes;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withIsSelfInvoicing(Boolean isSelfInvoicing) {
        this.isSelfInvoicing = isSelfInvoicing;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withFormType(InvoiceFormType formType) {
        this.formType = formType;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withInvoiceTypes(List<InvoiceMetadataInvoiceType> invoiceTypes) {
        this.invoiceTypes = invoiceTypes;
        return this;
    }

    public InvoicesAsyncQueryFiltersBuilder withHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
        return this;
    }

    public InvoiceExportFilters build() {
        InvoiceExportFilters request = new InvoiceExportFilters();
        request.setSubjectType(subjectType);
        request.setDateRange(dateRange);
        request.setKsefNumber(ksefNumber);
        request.setInvoiceNumber(invoiceNumber);
        request.setAmount(amount);
        request.setSellerNip(sellerNip);
        request.setBuyerIdentifier(buyer);
        request.setCurrencyCodes(currencyCodes);
        request.setInvoicingMode(invoicingMode);
        request.setIsSelfInvoicing(isSelfInvoicing);
        request.setFormType(formType);
        request.setInvoiceTypes(invoiceTypes);
        request.setHasAttachment(hasAttachment);
        return request;
    }
}


package pl.akmf.ksef.sdk.client.model.invoice;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;


public class InvoiceMetadata {
    private String ksefNumber;
    private String invoiceNumber;
    private OffsetDateTime invoicingDate;
    private LocalDate issueDate;
    private OffsetDateTime acquisitionDate;
    private OffsetDateTime permanentStorageDate;
    private InvoiceMetadataSeller seller;
    private InvoiceMetadataBuyer buyer;
    private Double netAmount;
    private Double grossAmount;
    private Double vatAmount;
    private String currency;
    private InvoicingMode invoicingMode;
    private InvoiceMetadataInvoiceType invoiceType;
    private InvoiceFormCode formCode;
    private Boolean isSelfInvoicing;
    private Boolean hasAttachment;
    private String invoiceHash;
    private String hashOfCorrectedInvoice;
    private AuthorizedSubject authorizedSubject;
    private List<ThirdSubject> thirdSubjects;

    public InvoiceMetadata() {
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public OffsetDateTime getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(OffsetDateTime acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public OffsetDateTime getInvoicingDate() {
        return invoicingDate;
    }

    public void setInvoicingDate(OffsetDateTime invoicingDate) {
        this.invoicingDate = invoicingDate;
    }

    public OffsetDateTime getPermanentStorageDate() {
        return permanentStorageDate;
    }

    public void setPermanentStorageDate(OffsetDateTime permanentStorageDate) {
        this.permanentStorageDate = permanentStorageDate;
    }

    public InvoiceMetadataSeller getSeller() {
        return seller;
    }

    public void setSeller(InvoiceMetadataSeller seller) {
        this.seller = seller;
    }

    public InvoiceMetadataBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(InvoiceMetadataBuyer buyer) {
        this.buyer = buyer;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public InvoicingMode getInvoicingMode() {
        return invoicingMode;
    }

    public void setInvoicingMode(InvoicingMode invoicingMode) {
        this.invoicingMode = invoicingMode;
    }

    public InvoiceMetadataInvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceMetadataInvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public InvoiceFormCode getFormCode() {
        return formCode;
    }

    public void setFormCode(InvoiceFormCode formCode) {
        this.formCode = formCode;
    }

    public Boolean getIsSelfInvoicing() {
        return isSelfInvoicing;
    }

    public void setIsSelfInvoicing(Boolean selfInvoicing) {
        isSelfInvoicing = selfInvoicing;
    }

    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public String getInvoiceHash() {
        return invoiceHash;
    }

    public void setInvoiceHash(String invoiceHash) {
        this.invoiceHash = invoiceHash;
    }

    public String getHashOfCorrectedInvoice() {
        return hashOfCorrectedInvoice;
    }

    public void setHashOfCorrectedInvoice(String hashOfCorrectedInvoice) {
        this.hashOfCorrectedInvoice = hashOfCorrectedInvoice;
    }

    public AuthorizedSubject getAuthorizedSubject() {
        return authorizedSubject;
    }

    public void setAuthorizedSubject(AuthorizedSubject authorizedSubject) {
        this.authorizedSubject = authorizedSubject;
    }

    public List<ThirdSubject> getThirdSubjects() {
        return thirdSubjects;
    }

    public void setThirdSubjects(List<ThirdSubject> thirdSubjects) {
        this.thirdSubjects = thirdSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceMetadata that = (InvoiceMetadata) o;
        return this.ksefNumber.equals(that.ksefNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ksefNumber);
    }
}


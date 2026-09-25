package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * InvoiceType
 */
public enum InvoiceMetadataInvoiceType {

    // (FA) Podstawowa
    VAT("Vat"),
    // (FA) Korygująca
    KOR("Kor"),
    // (FA) Zaliczkowa
    ZAL("Zal"),
    // (FA) Rozliczeniowa
    ROZ("Roz"),
    // (FA) Uproszczona
    UPR("Upr"),
    // (FA) Korygująca fakturę zaliczkową
    KOR_ZAL("KorZal"),
    // (FA) Korygująca fakturę rozliczeniową
    KOR_ROZ("KorRoz"),
    // (PEF) Podstawowa
    VAT_PEF("VatPef"),
    // (PEF) Specjalizowana
    VAT_PEF_SP("VatPefSp"),
    // (PEF) Korygująca
    KOR_PEF("KorPef"),
    //  	(FA_RR) Podstawowa
    VAT_RR("VatRr"),
    // (FA_RR) Korygująca
    KOR_VAT_RR("KorVatRr");

    private final String value;

    InvoiceMetadataInvoiceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static InvoiceMetadataInvoiceType fromValue(String value) {
        for (InvoiceMetadataInvoiceType b : InvoiceMetadataInvoiceType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

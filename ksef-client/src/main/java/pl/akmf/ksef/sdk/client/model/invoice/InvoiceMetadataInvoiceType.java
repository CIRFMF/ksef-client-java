package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets InvoiceMetadataInvoiceType
 */
public enum InvoiceMetadataInvoiceType {

    VAT("Vat"), // (FA) Podstawowa

    KOR("Kor"), // (FA) Korygująca

    ZAL("Zal"), // (FA) Zaliczkowa

    ROZ("Roz"), // (FA) Rozliczeniowa

    UPR("Upr"), // (FA) Uproszczona

    KOR_ZAL("KorZal"), // (FA) Korygująca fakturę zaliczkową

    KOR_ROZ("KorRoz"), // (FA) Korygująca fakturę rozliczeniową

    VAT_PEF("VatPef"), // (PEF) Podstawowa

    VAT_PEF_SP("VatPefSp"), // (PEF) Specjalizowana

    KOR_PEF("KorPef"), // (PEF) Korygująca

    VAT_RR("VatRr"), //  	(FA_RR) Podstawowa

    @Deprecated
    KOR_VAT_SP("KorVatRr"), // (FA_RR) Korygująca

    KOR_VAT_RR("KorVatRr"); // (FA_RR) Korygująca

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


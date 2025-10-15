package pl.akmf.ksef.sdk.client.model.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets InvoiceMetadataInvoiceType
 */
public enum InvoiceMetadataInvoiceType {

    VAT("Vat"),

    KOR("Kor"),

    ZAL("Zal"),

    ROZ("Roz"),

    UPR("Upr"),

    KOR_ZAL("KorZal"),

    KOR_ROZ("KorRoz"),

    VAT_PEF("VatPef"),

    KOR_PEF("KorPef"),

    VAT_RR("VatRr"),

    KOR_VAT_SP("KorVatRr");

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


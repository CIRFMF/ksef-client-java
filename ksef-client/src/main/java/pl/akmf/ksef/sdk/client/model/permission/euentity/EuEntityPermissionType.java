package pl.akmf.ksef.sdk.client.model.permission.euentity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EuEntityPermissionType {
  
  INVOICEWRITE("InvoiceWrite"),
  INVOICEREAD("InvoiceRead");

  private final String value;

  EuEntityPermissionType(String value) {
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

}


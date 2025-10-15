package pl.akmf.ksef.sdk.client.model.permission.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityPermissionType {
  
  INVOICE_WRITE("InvoiceWrite"),

  INVOICE_READ("InvoiceRead");

  private final String value;

  EntityPermissionType(String value) {
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


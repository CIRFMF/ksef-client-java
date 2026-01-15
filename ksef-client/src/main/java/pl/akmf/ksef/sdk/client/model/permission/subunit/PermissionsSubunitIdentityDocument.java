package pl.akmf.ksef.sdk.client.model.permission.subunit;

public class PermissionsSubunitIdentityDocument {
    private String type;
    private String number;
    private String country;

    public PermissionsSubunitIdentityDocument() {
    }

    public PermissionsSubunitIdentityDocument(String type, String number, String country) {
        this.type = type;
        this.number = number;
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

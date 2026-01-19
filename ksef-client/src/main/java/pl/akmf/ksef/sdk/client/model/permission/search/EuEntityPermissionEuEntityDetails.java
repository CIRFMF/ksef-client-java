package pl.akmf.ksef.sdk.client.model.permission.search;

public class EuEntityPermissionEuEntityDetails {
    private String fullName;
    private String address;

    public EuEntityPermissionEuEntityDetails() {

    }

    public EuEntityPermissionEuEntityDetails(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

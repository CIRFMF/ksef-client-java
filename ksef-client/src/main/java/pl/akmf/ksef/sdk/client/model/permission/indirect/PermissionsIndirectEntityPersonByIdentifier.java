package pl.akmf.ksef.sdk.client.model.permission.indirect;

public class PermissionsIndirectEntityPersonByIdentifier {
    private String firstName;
    private String lastName;

    public PermissionsIndirectEntityPersonByIdentifier() {
    }

    public PermissionsIndirectEntityPersonByIdentifier(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

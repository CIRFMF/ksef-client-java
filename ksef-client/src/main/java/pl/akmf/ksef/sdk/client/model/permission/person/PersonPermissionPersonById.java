package pl.akmf.ksef.sdk.client.model.permission.person;

public class PersonPermissionPersonById {
    private String firstName;
    private String lastName;

    public PersonPermissionPersonById() {
    }

    public PersonPermissionPersonById(String firstName, String lastName) {
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

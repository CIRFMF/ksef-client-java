package pl.akmf.ksef.sdk.client.model.permission.euentity;

public class PermissionsEuEntityPersonByFpWithId {
    private String firstName;
    private String lastName;
    private Identifier identifier;

    public PermissionsEuEntityPersonByFpWithId() {
    }

    public PermissionsEuEntityPersonByFpWithId(String firstName, String lastName, Identifier identifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifier = identifier;
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

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public static class Identifier {
        private String value;
        private IdentifierType type;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public IdentifierType getType() {
            return type;
        }

        public void setType(IdentifierType type) {
            this.type = type;
        }
    }

    public enum IdentifierType {
        Pesel,
        Nip
    }
}


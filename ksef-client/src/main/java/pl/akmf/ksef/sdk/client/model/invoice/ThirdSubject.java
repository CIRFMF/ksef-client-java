package pl.akmf.ksef.sdk.client.model.invoice;

public class ThirdSubject {
    private ThirdSubjectIdentifier identifier;
    private String name;
    private int role;

    public ThirdSubject(final ThirdSubjectIdentifier identifier,
                        final String name,
                        final int role) {
        this.identifier = identifier;
        this.name = name;
        this.role = role;
    }

    public ThirdSubject() {

    }

    public ThirdSubjectIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final ThirdSubjectIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(final int role) {
        this.role = role;
    }
}

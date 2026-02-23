package pl.akmf.ksef.sdk.client.model.permission.search;

import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionIdentityDocument;
import pl.akmf.ksef.sdk.client.model.permission.person.PersonPermissionSubjectDetailsType;

public class PersonPermissionSubjectPersonDetails {
    private PersonPermissionSubjectDetailsType subjectDetailsType;
    private String firstName;
    private String lastName;
    private PersonPermissionPersonIdentifier personIdentifier;
    private String birthDate;
    private PersonPermissionIdentityDocument idDocument;

    public PersonPermissionSubjectPersonDetails() {
    }

    public PersonPermissionSubjectPersonDetails(PersonPermissionSubjectDetailsType subjectDetailsType, String firstName, String lastName, PersonPermissionPersonIdentifier personIdentifier, String birthDate, PersonPermissionIdentityDocument idDocument) {
        this.subjectDetailsType = subjectDetailsType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personIdentifier = personIdentifier;
        this.birthDate = birthDate;
        this.idDocument = idDocument;
    }

    public PersonPermissionSubjectDetailsType getSubjectDetailsType() {
        return subjectDetailsType;
    }

    public void setSubjectDetailsType(PersonPermissionSubjectDetailsType subjectDetailsType) {
        this.subjectDetailsType = subjectDetailsType;
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

    public PersonPermissionPersonIdentifier getPersonIdentifier() {
        return personIdentifier;
    }

    public void setPersonIdentifier(PersonPermissionPersonIdentifier personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public PersonPermissionIdentityDocument getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(PersonPermissionIdentityDocument idDocument) {
        this.idDocument = idDocument;
    }
}

package pl.akmf.ksef.sdk.client.model.certificate;

import java.util.List;

public class CertificateEnrollmentsInfoResponse {
    private List<String> givenNames;
    private String surname;
    private String commonName;
    private String serialNumber;
    private String countryName;
    private String uniqueIdentifier;
    private String organizationName;
    private String organizationIdentifier;

    public CertificateEnrollmentsInfoResponse() {
    }

    public List<String> getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(List<String> givenNames) {
        this.givenNames = givenNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationIdentifier() {
        return organizationIdentifier;
    }

    public void setOrganizationIdentifier(String organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
    }
}


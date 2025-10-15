package pl.akmf.ksef.sdk.api.builders.certificate;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.util.List;
import java.util.Objects;

public class CertificateBuilders {
    X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);

    public CertificateBuilders withOrganizationName(String organizationName) {
        if (StringUtils.isNotBlank(organizationName)) {
            nameBuilder.addRDN(BCStyle.O, organizationName);
        }
        return this;
    }

    public CertificateBuilders withOrganizationIdentifier(String organizationIdentifier) {
        if (StringUtils.isNotBlank(organizationIdentifier)) {
            nameBuilder.addRDN(BCStyle.ORGANIZATION_IDENTIFIER, organizationIdentifier);
        }
        return this;
    }

    public CertificateBuilders withCommonName(String commonName) {
        if (StringUtils.isNotBlank(commonName)) {
            nameBuilder.addRDN(BCStyle.CN, commonName);
        }
        return this;
    }

    public CertificateBuilders withSerialNumber(String serialNumber) {
        if (StringUtils.isNotBlank(serialNumber)) {
            nameBuilder.addRDN(BCStyle.SERIALNUMBER, serialNumber);
        }
        return this;
    }

    public CertificateBuilders withGivenName(String givenName) {
        if (StringUtils.isNotBlank(givenName)) {
            nameBuilder.addRDN(BCStyle.GIVENNAME, givenName);
        }
        return this;
    }

    public CertificateBuilders withGivenNames(List<String> givenNames) {
        if (Objects.nonNull(givenNames)) {
            givenNames.stream()
                    .filter(StringUtils::isNotBlank)
                    .forEach(z -> nameBuilder.addRDN(BCStyle.GIVENNAME, z));
        }
        return this;
    }

    public CertificateBuilders withSurname(String surname) {
        if (StringUtils.isNotBlank(surname)) {
            nameBuilder.addRDN(BCStyle.SURNAME, surname);
        }
        return this;
    }

    public CertificateBuilders withUniqueIdentifier(String uniqueIdentifier) {
        if (StringUtils.isNotBlank(uniqueIdentifier)) {
            nameBuilder.addRDN(BCStyle.UNIQUE_IDENTIFIER, uniqueIdentifier);
        }
        return this;
    }

    public CertificateBuilders withCountryCode(String countryCode) {
        if (StringUtils.isNotBlank(countryCode)) {
            nameBuilder.addRDN(BCStyle.C, countryCode);
        }
        return this;
    }

    public X500NameHolder build() {
        X500Name x500Name = nameBuilder.build();

        return new X500NameHolder(x500Name);
    }

    public X500NameHolder buildForOrganization(String organizationName, String organizationIdentifier, String commonName, String countryCode) {
        withOrganizationIdentifier(organizationIdentifier);
        withOrganizationName(organizationName);
        withCommonName(commonName);
        withCountryCode(countryCode);

        return build();
    }

    public X500NameHolder buildForPerson(String givenName, String surname, String serialNumber, String commonName, String countryCode) {
        withGivenName(givenName);
        withSurname(surname);
        withSerialNumber(serialNumber);
        withCommonName(commonName);
        withCountryCode(countryCode);

        return build();
    }

    public static class X500NameHolder {
        private final X500Name x500Name;

        public X500NameHolder(X500Name x500Name) {
            this.x500Name = x500Name;
        }

        public X500Name getX500Name() {
            return this.x500Name;
        }
    }
}

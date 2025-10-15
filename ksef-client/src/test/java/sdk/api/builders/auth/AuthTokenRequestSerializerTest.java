package sdk.api.builders.auth;

import jakarta.xml.bind.JAXBException;
import org.junit.Test;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestBuilder;
import pl.akmf.ksef.sdk.api.builders.auth.AuthTokenRequestSerializer;
import pl.akmf.ksef.sdk.client.model.xml.AuthTokenRequest;
import pl.akmf.ksef.sdk.client.model.xml.SubjectIdentifierTypeEnum;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthTokenRequestSerializerTest {

    private static final String EXPECTED_XML_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AuthTokenRequest xmlns=\"http://ksef.mf.gov.pl/auth/token/2.0\">\n" +
            "    <Challenge>222-222-2222-2222</Challenge>\n" +
            "    <ContextIdentifier>\n" +
            "        <Nip>111111111</Nip>\n" +
            "    </ContextIdentifier>\n" +
            "    <SubjectIdentifierType>certificateSubject</SubjectIdentifierType>\n" +
            "    <IpAddressPolicy>\n" +
            "        <OnClientIpChange>reject</OnClientIpChange>\n" +
            "        <AllowedIps>\n" +
            "            <IpAddress>321</IpAddress>\n" +
            "            <IpRange>range</IpRange>\n" +
            "            <IpMask>mask</IpMask>\n" +
            "        </AllowedIps>\n" +
            "    </IpAddressPolicy>\n" +
            "</AuthTokenRequest>\n";

    @Test
    public void shouldReturnXmlFile() throws JAXBException {
        //given: create token object
        AuthTokenRequest authTokenRequest = new AuthTokenRequestBuilder()
                .withChallenge("222-222-2222-2222")
                .withContextNip( "111111111")
                .withSubjectType(SubjectIdentifierTypeEnum.CERTIFICATE_SUBJECT)
                .withIpAddressPolicy("reject", List.of("321"), List.of("range"), List.of("mask"))
                .build();


        //when: replace xml tags
        var parseXml = AuthTokenRequestSerializer.authTokenRequestSerializer(authTokenRequest);

        //then:
        assertEquals(EXPECTED_XML_VALUE, parseXml);
    }
}

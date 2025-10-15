package pl.akmf.ksef.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.akmf.ksef.sdk.api.DefaultKsefClient;
import pl.akmf.ksef.sdk.api.services.DefaultCertificateService;
import pl.akmf.ksef.sdk.api.services.DefaultCryptographyService;
import pl.akmf.ksef.sdk.api.services.DefaultQrCodeService;
import pl.akmf.ksef.sdk.api.services.DefaultSignatureService;
import pl.akmf.ksef.sdk.api.services.DefaultVerificationLinkService;
import pl.akmf.ksef.sdk.client.interfaces.CertificateService;
import pl.akmf.ksef.sdk.client.interfaces.KSeFClient;
import pl.akmf.ksef.sdk.client.interfaces.QrCodeService;
import pl.akmf.ksef.sdk.client.interfaces.SignatureService;
import pl.akmf.ksef.sdk.client.interfaces.VerificationLinkService;
import pl.akmf.ksef.sdk.util.ExampleApiProperties;
import pl.akmf.ksef.sdk.util.HttpClientBuilder;
import pl.akmf.ksef.sdk.util.HttpClientConfig;

import java.net.http.HttpClient;

@Configuration
@RequiredArgsConstructor
public class KsefClientConfig {

    @Bean
    public ExampleApiProperties apiProperties() {
        return new ExampleApiProperties();
    }

    @Bean
    public CertificateService initDefaultCertificateService() {
        return new DefaultCertificateService();
    }

    @Bean
    public SignatureService initDefaultSignatureService() {
        return new DefaultSignatureService();
    }

    @Bean
    public VerificationLinkService initDefaultVerificationLinkService() {
        return new DefaultVerificationLinkService();
    }

    @Bean
    public QrCodeService initDefaultQrCodeService() {
        return new DefaultQrCodeService();
    }

    @Bean
    public DefaultKsefClient initDefaultKsefClient() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        HttpClient apiClient = HttpClientBuilder.createHttpBuilder(new HttpClientConfig()).build();
        return new DefaultKsefClient(
                apiClient,
                apiProperties(),
                objectMapper);
    }

    @Bean
    public DefaultCryptographyService initDefaultCryptographyService(KSeFClient kSeFClient) {
        return new DefaultCryptographyService(kSeFClient);
    }
}

package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoiceStatusResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionInvoicesResponse;
import pl.akmf.ksef.sdk.client.model.session.SessionStatusResponse;

import java.util.ArrayList;
import java.util.List;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;
import static pl.akmf.ksef.sdk.client.Headers.CONTINUATION_TOKEN;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {
    private final DefaultKsefClient ksefClient;

    @GetMapping("/status")
    public SessionStatusResponse getStatusAsync(@RequestParam String referenceNumber,
                                                @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getSessionStatus(referenceNumber, authToken);

    }

    @GetMapping("/invoice-upo")
    public byte[] getInvoiceUpoAsync(
            @RequestParam String referenceNumber,
            @RequestParam String ksefNumber,
            @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getSessionInvoiceUpoByKsefNumber(referenceNumber, ksefNumber, authToken);

    }

    @GetMapping("/invoice-upo-by-invoice-number")
    public byte[] getInvoiceUpoByReferenceNumber(
            @RequestParam String referenceNumber,
            @RequestParam String invoiceReferenceNumber,
            @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getSessionInvoiceUpoByReferenceNumber(referenceNumber, invoiceReferenceNumber, authToken);
    }

    @GetMapping("/session-upo")
    public byte[] getSessionUpoAsync(
            @RequestParam String referenceNumber,
            @RequestParam String upoReferenceNumber,
            @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getSessionUpo(referenceNumber, upoReferenceNumber, authToken);
    }


    @GetMapping("/session-documents")
    public List<SessionInvoiceStatusResponse> getSessionDocumentsAsync(
            @RequestParam String referenceNumber,
            @RequestHeader(name = AUTHORIZATION) String authToken,
            @RequestHeader(name = CONTINUATION_TOKEN, required = false, defaultValue = "") String continuationToken) throws ApiException {

        SessionInvoicesResponse response = ksefClient.getSessionInvoices(referenceNumber, continuationToken, 10, authToken);
        List<SessionInvoiceStatusResponse> sessionInvoices = new ArrayList<>(response.getInvoices());

        while (Strings.isNotBlank(response.getContinuationToken())) {
            response = ksefClient.getSessionInvoices(referenceNumber, response.getContinuationToken(), 10, authToken);
            sessionInvoices.addAll(response.getInvoices());
        }

        return sessionInvoices;
    }

    @GetMapping("/failed-invoices")
    public List<SessionInvoiceStatusResponse> getFailedInvoicesAsync(
            @RequestParam String referenceNumber, @RequestParam Integer pageSize,
            @RequestHeader(name = AUTHORIZATION) String authToken,
            @RequestHeader(name = CONTINUATION_TOKEN, required = false, defaultValue = "") String continuationToken) throws ApiException {
        SessionInvoicesResponse response = ksefClient.getSessionFailedInvoices(referenceNumber, continuationToken, pageSize, authToken);
        List<SessionInvoiceStatusResponse> sessionInvoices = new ArrayList<>(response.getInvoices());

        while (Strings.isNotBlank(response.getContinuationToken())) {
            response = ksefClient.getSessionInvoices(referenceNumber, response.getContinuationToken(), pageSize, authToken);
            sessionInvoices.addAll(response.getInvoices());
        }

        return sessionInvoices;
    }

    @GetMapping("/invoice-status")
    SessionInvoiceStatusResponse getSessionInvoiceStatus(
            @RequestParam String referenceNumber,
            @RequestParam String invoiceReferenceNumber,
            @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getSessionInvoiceStatus(referenceNumber, invoiceReferenceNumber, authToken);
    }
}

package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.invoice.InitAsyncInvoicesQueryResponse;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportRequest;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceExportStatus;
import pl.akmf.ksef.sdk.client.model.invoice.InvoiceQueryFilters;
import pl.akmf.ksef.sdk.client.model.invoice.QueryInvoiceMetadataResponse;
import pl.akmf.ksef.sdk.client.model.util.SortOrder;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class InvoicesController {
    private final DefaultKsefClient ksefClient;

    @GetMapping("/invoices/ksef/{ksefReferenceNumber}")
    public byte[] getInvoice(@PathVariable String ksefReferenceNumber,
                             @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.getInvoice(ksefReferenceNumber, authToken);
    }

    @PostMapping("/invoices/metadata")
    QueryInvoiceMetadataResponse getInvoiceMetadata(@RequestParam Integer pageOffset,
                                                    @RequestParam Integer pageSize,
                                                    @RequestBody InvoiceQueryFilters request,
                                                    @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.queryInvoiceMetadata(pageOffset, pageSize, SortOrder.ASC, request, authToken);
    }

    @PostMapping("/invoices/query/async")
    InitAsyncInvoicesQueryResponse initAsyncInvoiceQuery(@RequestBody InvoiceExportRequest request,
                                                         @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.initAsyncQueryInvoice(request, authToken);
    }

    @GetMapping("/invoices/query/async")
    InvoiceExportStatus checkStatusAsyncQueryInvoice(@RequestParam String operationReferenceNumber,
                                                     @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.checkStatusAsyncQueryInvoice(operationReferenceNumber, authToken);
    }
}

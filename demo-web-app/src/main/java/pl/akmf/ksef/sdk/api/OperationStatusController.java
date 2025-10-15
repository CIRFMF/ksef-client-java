package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class OperationStatusController {
    private final DefaultKsefClient ksefClient;

    @GetMapping("{referenceNumber}/status")
    public PermissionStatusInfo getOperationStatusAsync(@PathVariable String referenceNumber,
                                                        @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.permissionOperationStatus(referenceNumber, authToken);
    }
}

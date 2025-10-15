package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionsGrantRequest;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class EuEntityPermissionsController {
    private final DefaultKsefClient ksefClient;

    @PostMapping(value = "/grant-eu-entity-permissions")
    public OperationResponse grantPermissionsEntity(@RequestBody EuEntityPermissionsGrantRequest request,
                                                    @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.grantsPermissionEUEntity(request, authToken);
    }

    @PostMapping(value = "/revoke-eu-entity-permissions")
    public OperationResponse revokePermissionsEntity(@RequestBody String permissionId,
                                                     @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.revokeCommonPermission(permissionId, authToken);
    }
}
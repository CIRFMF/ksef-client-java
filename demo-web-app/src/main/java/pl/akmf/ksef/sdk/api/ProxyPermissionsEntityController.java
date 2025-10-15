package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.proxy.GrantAuthorizationPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.proxy.SubjectIdentifier;
import pl.akmf.ksef.sdk.client.model.permission.search.InvoicePermissionType;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProxyPermissionsEntityController {
    private final DefaultKsefClient ksefClient;

    @PostMapping("/grant-proxy-permissions-for-entity")
    public OperationResponse grantPermissionsEntity(@RequestBody SubjectIdentifier subjectIdentifier,
                                                    @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        GrantAuthorizationPermissionsRequest request = new GrantAuthorizationPermissionsRequest();
        request.setSubjectIdentifier(subjectIdentifier);
        request.setPermission(InvoicePermissionType.TAX_REPRESENTATIVE);
        request.setDescription("Access for quarterly review");

        OperationResponse operationResponse = ksefClient.grantsPermissionsProxyEntity(request, authToken);
        log.info(operationResponse.toString());
        return operationResponse;
    }

    @PostMapping("/revoke-proxy-permissions-for-entity")
    public OperationResponse revokePermissionsEntity(@RequestBody String permissionId,
                                                     @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.revokeAuthorizationsPermission(permissionId, authToken);
    }
}

package pl.akmf.ksef.sdk.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.PermissionStatusInfo;
import pl.akmf.ksef.sdk.client.model.permission.person.GrantPersonPermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.PersonPermissionsQueryRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonPermissionsResponse;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantRequest;
import pl.akmf.ksef.sdk.client.model.permission.search.QueryPersonalGrantResponse;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class PermissionEndpoint {
    private final DefaultKsefClient ksefClient;

    @PostMapping("/permission/myCredential")
    public QueryPersonalGrantResponse permissionSearchOwnCredential(@RequestBody QueryPersonalGrantRequest request,
                                                                    @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.searchPersonalGrantPermission(request,0,10, authToken);
    }

    @PostMapping("/permission/search")
    public QueryPersonPermissionsResponse permissionSearch(@RequestBody PersonPermissionsQueryRequest request,
                                                           @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.searchGrantedPersonPermissions(request, 0, 10, authToken);
    }

    @GetMapping("/permission/{referenceNumber}/status")
    public PermissionStatusInfo permissionStatus(@PathVariable String referenceNumber,
                                                 @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.permissionOperationStatus(referenceNumber, authToken);
    }

    @PostMapping("/permission/grant")
    public OperationResponse permissionGrant(@RequestBody GrantPersonPermissionsRequest request,
                                             @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.grantsPermissionPerson(request, authToken);
    }

    @PostMapping("/permission/revoke/{permissionId}")
    public OperationResponse permissionRevoke(
            @PathVariable String permissionId,
            @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.revokeCommonPermission(permissionId, authToken);
    }
}

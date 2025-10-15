package pl.akmf.ksef.sdk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.akmf.ksef.sdk.api.builders.permission.euentityrepresentative.GrantEUEntityRepresentativePermissionsRequestBuilder;
import pl.akmf.ksef.sdk.client.model.ApiException;
import pl.akmf.ksef.sdk.client.model.permission.OperationResponse;
import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.euentity.SubjectIdentifier;

import java.util.List;

import static pl.akmf.ksef.sdk.client.Headers.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class EuEntityRepresentativePermissionsController {
    private final DefaultKsefClient ksefClient;

    @PostMapping(value = "/grant-eu-entity-representative-permissions")
    public OperationResponse grantPermissionsEntity(@RequestBody SubjectIdentifier subjectIdentifier,
                                                    @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        var request = new GrantEUEntityRepresentativePermissionsRequestBuilder()
                .withSubjectIdentifier(subjectIdentifier)
                .withPermissions(List.of(EuEntityPermissionType.INVOICE_READ, EuEntityPermissionType.INVOICE_WRITE))
                .withDescription("Representative access")
                .build();

        return ksefClient.grantsPermissionEUEntityRepresentative(request, authToken);
    }

    @PostMapping(value = "revoke-eu-entity-representative-permissions")
    public OperationResponse revokePermissionsEntity(@RequestBody String permissionId,
                                                     @RequestHeader(name = AUTHORIZATION) String authToken) throws ApiException {
        return ksefClient.revokeAuthorizationsPermission(permissionId, authToken);
    }
}

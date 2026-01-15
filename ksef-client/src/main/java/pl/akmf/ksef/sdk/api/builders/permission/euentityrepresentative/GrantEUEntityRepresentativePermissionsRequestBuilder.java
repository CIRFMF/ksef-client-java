package pl.akmf.ksef.sdk.api.builders.permission.euentityrepresentative;

import pl.akmf.ksef.sdk.client.model.permission.euentity.EuEntityPermissionType;
import pl.akmf.ksef.sdk.client.model.permission.euentity.GrantEUEntityRepresentativePermissionsRequest;
import pl.akmf.ksef.sdk.client.model.permission.euentity.PermissionsEuEntitySubjectDetails;
import pl.akmf.ksef.sdk.client.model.permission.euentity.SubjectIdentifier;

import java.util.List;

public class GrantEUEntityRepresentativePermissionsRequestBuilder {
    private SubjectIdentifier subjectIdentifier;
    private List<EuEntityPermissionType> permissions;
    private String description;
    private PermissionsEuEntitySubjectDetails subjectDetails;

    public GrantEUEntityRepresentativePermissionsRequestBuilder withSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
        return this;
    }

    public GrantEUEntityRepresentativePermissionsRequestBuilder withPermissions(List<EuEntityPermissionType> permissions) {
        this.permissions = permissions;
        return this;
    }

    public GrantEUEntityRepresentativePermissionsRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GrantEUEntityRepresentativePermissionsRequestBuilder withSubjectDetails(PermissionsEuEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
        return this;
    }


    public GrantEUEntityRepresentativePermissionsRequest build() {
        GrantEUEntityRepresentativePermissionsRequest request = new GrantEUEntityRepresentativePermissionsRequest();
        request.setSubjectIdentifier(subjectIdentifier);
        request.setPermissions(permissions);
        request.setDescription(description);
        request.setSubjectDetails(subjectDetails);
        return request;
    }
}

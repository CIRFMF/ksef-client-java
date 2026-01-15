package pl.akmf.ksef.sdk.client.model.permission.entity;

import java.util.List;

public class GrantEntityPermissionsRequest {
    private SubjectIdentifier subjectIdentifier;
    private List<EntityPermission> permissions;
    private String description;
    private PermissionsEntitySubjectDetails subjectDetails;

    public GrantEntityPermissionsRequest() {
    }

    public GrantEntityPermissionsRequest(SubjectIdentifier subjectIdentifier, List<EntityPermission> permissions, String description, PermissionsEntitySubjectDetails subjectDetails) {
        this.subjectIdentifier = subjectIdentifier;
        this.permissions = permissions;
        this.description = description;
        this.subjectDetails = subjectDetails;
    }

    public SubjectIdentifier getSubjectIdentifier() {
        return subjectIdentifier;
    }

    public void setSubjectIdentifier(SubjectIdentifier subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public List<EntityPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<EntityPermission> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionsEntitySubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(PermissionsEntitySubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    public class PermissionsEntitySubjectDetails {
        private String fullName;

        public PermissionsEntitySubjectDetails() {
        }

        public PermissionsEntitySubjectDetails(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}


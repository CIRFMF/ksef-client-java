package pl.akmf.ksef.sdk.client.model.permission;

import java.time.OffsetDateTime;

public class PermissionAttachmentStatusResponse {
    private Boolean isAttachmentAllowed;
    private OffsetDateTime revokedDate;

    public PermissionAttachmentStatusResponse() {

    }

    public Boolean getIsAttachmentAllowed() {
        return isAttachmentAllowed;
    }

    public void setIsAttachmentAllowed(Boolean isAttachmentAllowed) {
        this.isAttachmentAllowed = isAttachmentAllowed;
    }

    public OffsetDateTime getRevokedDate() {
        return revokedDate;
    }

    public void setRevokedDate(OffsetDateTime revokedDate) {
        this.revokedDate = revokedDate;
    }
}

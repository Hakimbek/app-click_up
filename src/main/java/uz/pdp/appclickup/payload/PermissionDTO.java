package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;

import java.util.Set;
import java.util.UUID;

@Data
public class PermissionDTO {
    private UUID roleId;

    private Set<WorkspacePermissionName> workspacePermissionNames;
}

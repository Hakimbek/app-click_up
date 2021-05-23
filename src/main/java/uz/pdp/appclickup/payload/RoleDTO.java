package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;

import java.util.Set;

@Data
public class RoleDTO {
    private String roleName;

    private Set<WorkspacePermissionName> workspacePermissionNames;
}

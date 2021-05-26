package uz.pdp.appclickup.service.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.workspace.WorkspacePermission;
import uz.pdp.appclickup.entity.workspace.WorkspaceRole;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionDTO;
import uz.pdp.appclickup.repository.WorkspacePermissionRepository;
import uz.pdp.appclickup.repository.WorkspaceRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;


    /**
     * ADD OR REMOVE PERMISSION
     *
     * @param permissionDTO ROLE ID, PERMISSION LIST
     * @return Api Response
     */
    @Override
    public ApiResponse addOrRemovePermission(PermissionDTO permissionDTO) {
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(permissionDTO.getRoleId());
        if (!optionalWorkspaceRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();

        workspacePermissionRepository.deleteAllByWorkspaceRoleId(workspaceRole.getId());

        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : permissionDTO.getWorkspacePermissionNames()) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    workspaceRole, workspacePermissionName
            );
            workspacePermissions.add(workspacePermission);
        }
        workspacePermissionRepository.saveAll(workspacePermissions);
        return new ApiResponse("Success", true);
    }
}

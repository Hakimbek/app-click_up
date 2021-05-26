package uz.pdp.appclickup.service.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.entity.workspace.WorkspacePermission;
import uz.pdp.appclickup.entity.workspace.WorkspaceRole;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.RoleDTO;
import uz.pdp.appclickup.repository.WorkspacePermissionRepository;
import uz.pdp.appclickup.repository.WorkspaceRepository;
import uz.pdp.appclickup.repository.WorkspaceRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;


    /**
     * ADD ROLE
     * @param id LONG
     * @param roleDTO ROLE NAME, PERMISSION LIST
     * @return Api Response
     */
    @Override
    public ApiResponse addRole(Long id, RoleDTO roleDTO) {
        // workspaceni oldim
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();

        // shunday name li role yoqligini tekshirdim
        if (workspaceRoleRepository.existsByName(roleDTO.getRoleName())) {
            return new ApiResponse("Role already exist", false);
        }

        // roleni saqladim
        WorkspaceRole savedRole = workspaceRoleRepository.save(
                new WorkspaceRole(workspace, roleDTO.getRoleName(), null)
        );

        // permissionlarni listga yig'dim
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : roleDTO.getWorkspacePermissionNames()) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    savedRole, workspacePermissionName
            );
            workspacePermissions.add(workspacePermission);
        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        return new ApiResponse("Success", true);
    }
}

package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.entity.workspace.WorkspaceUser;
import uz.pdp.appclickup.payload.*;

import java.util.List;
import java.util.UUID;


public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId, User user);

    List<WorkspaceUser> getMembers(Long id);

    List<WorkspaceUser> getGuests(Long id);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO);

    ApiResponse joinToWorkspace(Long id, User user);

    List<Workspace> getWorkspaces(User user);

    ApiResponse addRole(Long id, RoleDTO roleDTO);

    ApiResponse addOrRemovePermission(PermissionDTO permissionDTO);
}

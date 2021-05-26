package uz.pdp.appclickup.service.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.entity.enums.AddType;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.enums.WorkspaceRoleName;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.entity.workspace.WorkspacePermission;
import uz.pdp.appclickup.entity.workspace.WorkspaceRole;
import uz.pdp.appclickup.entity.workspace.WorkspaceUser;
import uz.pdp.appclickup.payload.*;
import uz.pdp.appclickup.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * WORKSPACE QOSHISH
     *
     * @param workspaceDTO NAME, COLOR, AVATAR ID
     * @param user         USER
     * @return Api Response
     */
    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user) {
        //WORKSPACE OCHDIK
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDTO.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);

        //WORKSPACE ROLE OCHDIK
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //OWERGA HUQUQLARNI BERYAPAMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //WORKSPACE USER OCHDIK
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        ));

        return new ApiResponse("Ishxona saqlandi", true);
    }


    /**
     * NAME, COLOR, AVATAR O'ZGARAISHI MUMKIN
     *
     * @param id           LONG
     * @param workspaceDTO NAME, COLOR, AVATAR ID
     * @return Api Response
     */
    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO) {
        // workspaceni oldim va mavjudligini tekshirdim
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isPresent()) {
            Workspace workspace = optionalWorkspace.get();

            // workspaceni edit qildim
            workspace.setName(workspaceDTO.getName());
            workspace.setColor(workspaceDTO.getColor());
            workspace.setAvatar(workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
            workspaceRepository.save(workspace);
            return new ApiResponse("Edited", true);
        }
        return new ApiResponse("Error", false);
    }


    /**
     * OWNERNI O'ZGARTIRISH
     *
     * @param id      LONG
     * @param newOwnerId UUID
     * @return Api Response
     */
    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID newOwnerId, User user) {
        // workspaceni oldim va mavjudligini tekshirdim
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();

        // yangi ownerni sistemada mavjudligini tekshirdim
        Optional<User> optionalUser = userRepository.findById(newOwnerId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User newOwner = optionalUser.get();

        // kirib turgan user shu workspacening owneriligini tekshirdim
        if (workspace.getOwner().getId().equals(user.getId())) {
            workspace.setOwner(newOwner);
            workspaceRepository.save(workspace);

            // eski ownerni roleni admin qilish uchun workspace role dan admin role ni oldim
            Optional<WorkspaceRole> workspaceRoleAdmin = workspaceRoleRepository.findByNameAndWorkspaceId(WorkspaceRoleName.ROLE_ADMIN.name(), workspace.getId());
            if (!workspaceRoleAdmin.isPresent()) {
                return new ApiResponse("Workspace role not found", false);
            }
            WorkspaceRole workspaceAdminRole = workspaceRoleAdmin.get();

            // yangi ownerni roleni owner qilish uchun workspace role dan owner role ni oldim
            Optional<WorkspaceRole> workspaceRoleOwner = workspaceRoleRepository.findByNameAndWorkspaceId(WorkspaceRoleName.ROLE_OWNER.name(), workspace.getId());
            if (!workspaceRoleOwner.isPresent()) {
                return new ApiResponse("Workspace role not found", false);
            }
            WorkspaceRole workspaceOwnerRole = workspaceRoleOwner.get();

            // eski ownerning workspace userini oldim
            Optional<WorkspaceUser> oldOwnerWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
            if (!oldOwnerWorkspaceUser.isPresent()) {
                return new ApiResponse("Workspace user not found", false);
            }

            // eski ownerning roleni admin qildim
            WorkspaceUser oldOwner = oldOwnerWorkspaceUser.get();
            oldOwner.setWorkspaceRole(workspaceAdminRole);
            workspaceUserRepository.save(oldOwner);

            // yangi ownerning workspace userini oldim
            Optional<WorkspaceUser> newOwnerWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspace.getId(), newOwner.getId());
            if (!newOwnerWorkspaceUser.isPresent()) {
                return new ApiResponse("Workspace user not found", false);
            }

            // yangi ownerning roleni owner qildim
            WorkspaceUser newOwnerUser = newOwnerWorkspaceUser.get();
            newOwnerUser.setWorkspaceRole(workspaceOwnerRole);
            workspaceUserRepository.save(newOwnerUser);

            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }


    /**
     * GET ALL MEMBERS
     *
     * @param id LONG
     * @return WORKSPACE USER LIST
     */
    public List<WorkspaceUser> getMembers(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ArrayList<>();
        }
        Workspace workspace = optionalWorkspace.get();

        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findByNameAndWorkspaceId(WorkspaceRoleName.ROLE_MEMBER.name(), workspace.getId());
        if (!optionalWorkspaceRole.isPresent()) {
            return new ArrayList<>();
        }
        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();

        return workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRoleId(workspace.getId(), workspaceRole.getId());
    }


    /**
     * GET ALL GUESTS
     *
     * @param id LONG
     * @return WORKSPACE USER LIST
     */
    @Override
    public List<WorkspaceUser> getGuests(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ArrayList<>();
        }
        Workspace workspace = optionalWorkspace.get();

        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findByNameAndWorkspaceId(WorkspaceRoleName.ROLE_GUEST.name(), workspace.getId());
        if (!optionalWorkspaceRole.isPresent()) {
            return new ArrayList<>();
        }
        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();

        return workspaceUserRepository.findAllByWorkspaceIdAndWorkspaceRoleId(workspace.getId(), workspaceRole.getId());
    }


    /**
     * GET ALL WORKSPACES
     *
     * @return Api Response
     */
    @Override
    public List<Workspace> getWorkspaces(User user) {
        return workspaceRepository.findAllByOwnerId(user.getId());
    }


    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id LONG
     * @return Api Response
     */
    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }


    /**
     * ADD OR EDIT OR REMOVE WORKSPACE USER
     *
     * @param id        LONG
     * @param memberDTO USER ID, ROLE ID, TYPE
     * @return Api Responce
     */
    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO) {
        if (memberDTO.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(memberDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepository.save(workspaceUser);

            //TODO EMAILGA INVITE XABAR YUBORISH
        } else if (memberDTO.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDTO.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDTO.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDTO.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);
    }


    /**
     * JOIN
     *
     * @param id   LONG
     * @param user USER
     * @return Api Responce
     */
    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }
}

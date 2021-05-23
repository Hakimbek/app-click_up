package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appclickup.entity.workspace.WorkspacePermission;

import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {
    @Transactional
    @Modifying
    void deleteAllByWorkspaceRoleId(UUID workspaceRole_id);
}

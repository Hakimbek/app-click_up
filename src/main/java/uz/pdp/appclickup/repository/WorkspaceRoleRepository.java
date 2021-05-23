package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.workspace.WorkspaceRole;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, UUID> {
    Optional<WorkspaceRole> findByNameAndWorkspaceId(String name, Long workspace_id);

    boolean existsByName(String name);

    Optional<WorkspaceRole> findByIdAndWorkspaceId(UUID id, Long workspace_id);
}

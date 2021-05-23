package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.space.Space;

import java.util.List;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {
    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);

    List<Space> findAllByWorkspaceId(Long workspace_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

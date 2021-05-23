package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.project.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);

    List<Project> findAllBySpaceId(UUID space_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

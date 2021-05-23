package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appclickup.entity.project.ProjectUser;

import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {
    boolean existsByProjectIdAndUserId(UUID project_id, UUID user_id);

    @Transactional
    @Modifying
    void deleteByProjectIdAndUserId(UUID project_id, UUID user_id);
}

package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.TaskUser;

import java.util.List;
import java.util.UUID;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {
    boolean existsByTaskIdAndUserId(UUID task_id, UUID user_id);

    List<TaskUser> findAllByTaskId(UUID task_id);
}

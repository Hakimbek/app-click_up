package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.TaskAttachment;

import java.util.List;
import java.util.UUID;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, UUID> {

    List<TaskAttachment> findAllByTaskId(UUID task_id);
}

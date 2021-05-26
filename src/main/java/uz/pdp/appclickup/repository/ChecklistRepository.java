package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.Checklist;

import java.util.List;
import java.util.UUID;

public interface ChecklistRepository extends JpaRepository<Checklist, UUID> {
    boolean existsByNameAndTaskId(String name, UUID task_id);

    List<Checklist> findAllByTaskId(UUID task_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

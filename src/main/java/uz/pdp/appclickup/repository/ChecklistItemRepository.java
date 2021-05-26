package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.CheckListItem;

import java.util.List;
import java.util.UUID;

public interface ChecklistItemRepository extends JpaRepository<CheckListItem, UUID> {
    boolean existsByNameAndChecklistId(String name, UUID checklist_id);

    List<CheckListItem> findAllByChecklistId(UUID checklist_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

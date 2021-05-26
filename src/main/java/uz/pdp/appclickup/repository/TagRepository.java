package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.Tag;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    boolean existsByNameAndWorkSpaceId(String name, Long workSpace_id);

    List<Tag> findAllByWorkSpaceId(Long workSpace_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

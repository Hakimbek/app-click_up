package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByName(String name);

    boolean existsByName(String name);

    List<Status> findAllBySpaceId(UUID space_id);

    List<Status> findAllByProjectId(UUID project_id);

    List<Status> findAllByCategoryId(UUID category_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.category.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameAndProjectId(String name, UUID project_id);

    List<Category> findAllByProjectId(UUID project_id);

    boolean existsByNameAndIdNot(String name, UUID id);
}

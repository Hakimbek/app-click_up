package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.category.CategoryUser;

import java.util.List;
import java.util.UUID;

public interface CategoryUserRepository extends JpaRepository<CategoryUser, UUID> {
    boolean existsByCategoryIdAndUserId(UUID category_id, UUID user_id);

    List<CategoryUser> findAllByCategoryId(UUID category_id);
}

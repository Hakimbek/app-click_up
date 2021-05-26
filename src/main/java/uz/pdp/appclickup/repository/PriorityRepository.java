package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.task.Priority;

import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}

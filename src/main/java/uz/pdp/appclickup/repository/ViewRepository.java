package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appclickup.entity.space.View;
import uz.pdp.appclickup.entity.task.Task;

import java.util.List;
import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, Long> {
    @Query(value = "select *\n" +
            "from task\n" +
            "where task.status_id =:statusId", nativeQuery = true)
    List<Task> getTasksByStatus(UUID statusId);


    @Query(value = "select *\n" +
            "from task\n" +
            "order by task.order_num ",  nativeQuery = true)
    List<Task> gatTasksByOrder();


    @Query(value = "select *\n" +
            "from task\n" +
            "where task.priority_id =:priorityId", nativeQuery = true)
    List<Task> getTasksByPriority(Long priorityId);


    @Query(value = "select *\n" +
            "from task\n" +
            "order by task.started_date", nativeQuery = true)
    List<Task> getTasksByStartTime(Long priorityId);



}

package uz.pdp.appclickup.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private Status status;

    private Integer orderNum;

    @ManyToOne
    private Priority priority;

    @ManyToOne
    private Task parentTask;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp startedDate;

    private Boolean startTimeHas;

    @UpdateTimestamp
    private Timestamp dueDate;

    private Boolean dueTimeHas;

    private Long estimateTime;

    private Timestamp activeDate;

    public Task(String name, String description, Category category, Status status, Priority priority, Task parentTask, Timestamp startedDate, Boolean startTimeHas, Timestamp dueDate, Boolean dueTimeHas, Long estimateTime, Timestamp activeDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.priority = priority;
        this.parentTask = parentTask;
        this.startedDate = startedDate;
        this.startTimeHas = startTimeHas;
        this.dueDate = dueDate;
        this.dueTimeHas = dueTimeHas;
        this.estimateTime = estimateTime;
        this.activeDate = activeDate;
    }
}

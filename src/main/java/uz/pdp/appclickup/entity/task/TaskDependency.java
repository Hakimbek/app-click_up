package uz.pdp.appclickup.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.DependencyType;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskDependency extends AbsUUIDEntity {
    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private Task dependencyTask;

    @Column(nullable = false)
    private DependencyType dependencyType;
}

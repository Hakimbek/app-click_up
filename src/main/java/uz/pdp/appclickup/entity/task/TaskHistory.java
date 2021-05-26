package uz.pdp.appclickup.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskHistory extends AbsUUIDEntity {
    @ManyToOne(optional = false)
    private Task task;

    private String changeFileName;

    private Object before;

    private Object after;

    private String data;
}

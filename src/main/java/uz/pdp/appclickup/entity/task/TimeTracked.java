package uz.pdp.appclickup.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
public class TimeTracked extends AbsUUIDEntity {
    @ManyToOne(optional = false)
    private Task task;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp startedAt;

    @UpdateTimestamp
    private Timestamp stoppedAt;
}

package uz.pdp.appclickup.entity.space;

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
public class SpaceClickApps extends AbsUUIDEntity {
    @ManyToOne(optional = false)
    private Space space;

    @ManyToOne(optional = false)
    private ClickApps clickApps;
}

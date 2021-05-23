package uz.pdp.appclickup.entity.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.Icon;
import uz.pdp.appclickup.entity.template.AbsLongEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class View extends AbsLongEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(optional = false)
    private Icon icon;
}

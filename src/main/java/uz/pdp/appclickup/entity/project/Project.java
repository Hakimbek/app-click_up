package uz.pdp.appclickup.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Space space;

    private Boolean accessType;

    private Boolean archived;

    @Column(nullable = false)
    private String color;
}

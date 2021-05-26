package uz.pdp.appclickup.entity.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Project project;

    private Boolean isPrivate;

    private Boolean archived;

    @Column(nullable = false)
    private String color;
}

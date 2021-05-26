package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.enums.StatusType;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status extends AbsUUIDEntity {
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private Space space;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private StatusType statusTypes;
}

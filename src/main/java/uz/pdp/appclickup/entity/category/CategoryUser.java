package uz.pdp.appclickup.entity.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.enums.ProjectPermissionName;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryUser extends AbsUUIDEntity {
    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    private ProjectPermissionName permissionNames;
}

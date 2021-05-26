package uz.pdp.appclickup.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.ProjectPermissionName;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUserDTO {
    @NotNull
    private UUID userId;

    private String email;

    @NotNull
    private ProjectPermissionName permissionNames;
}

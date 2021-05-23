package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.ProjectPermissionName;

import java.util.UUID;

@Data
public class ProjectUserDTO {
    private UUID userId;

    private ProjectPermissionName projectPermissionName;
}

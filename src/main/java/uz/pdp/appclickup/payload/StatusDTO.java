package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.StatusType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class StatusDTO {
    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID spaceId;

    private UUID projectId;

    private UUID categoryId;

    @NotNull
    private StatusType statusType;
}

package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
public class SpaceDTO {
    @NotNull
    private String name;

    @NotNull
    private String color;

    @NotNull
    private Long workspaceId;

    private Long iconId;

    private UUID attachmentId;

    @NotNull
    private UUID ownerId;

    @NotNull
    private Boolean accessType;

    private Set<UUID> users;
}

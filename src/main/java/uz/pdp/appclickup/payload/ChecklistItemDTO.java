package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ChecklistItemDTO {
    @NotNull
    private String name;

    private Boolean resolved;

    private UUID assignUserId;
}

package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CategoryDTO {
    @NotNull
    private String name;

    @NotNull
    private UUID projectId;

    private Boolean isPrivate;

    private Boolean archived;

    @NotNull
    private String color;
}

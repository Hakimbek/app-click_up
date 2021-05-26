package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TagDTO {
    @NotNull
    private String name;

    @NotNull
    private String color;
}

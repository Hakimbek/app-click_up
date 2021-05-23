package uz.pdp.appclickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDTO {
    private String name;

    private UUID spaceId;

    private boolean isPrivate;

    private boolean isArchived;

    private String color;
}

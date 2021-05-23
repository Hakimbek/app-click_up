package uz.pdp.appclickup.entity.enums;

public enum ProjectPermissionName {
    FULL("FULL", "Full access. Can creat tasks"),
    VIEW_ONLY("VIEW_ONLY", "Read-only. Can not comment or edit"),
    COMMENT("COMMENT", "Can comment"),
    EDIT("EDIT", "Can edit but can not edit tasks");

    private String name;
    private String description;

    ProjectPermissionName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

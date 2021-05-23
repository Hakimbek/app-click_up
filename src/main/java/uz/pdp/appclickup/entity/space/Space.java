package uz.pdp.appclickup.entity.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.Attachment;
import uz.pdp.appclickup.entity.Icon;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Space extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(optional = false)
    private Workspace workspace;

    private String initialLetter;

    @OneToOne
    private Icon icon = null;

    @OneToOne
    private Attachment avatar = null;

    @ManyToOne(optional = false)
    private User owner;

    private Boolean isPrivate;


    // set initial letter
    public void setInitialLetter() {
        String[] words = this.name.split(" ");
        if (words.length > 1) {
            this.initialLetter = words[0].charAt(0) + words[words.length - 1].substring(0, 1);
        } else {
            this.initialLetter = words[0].substring(0, 1);
        }
    }

    public Space(String name, String color, Workspace workspace, User owner, Boolean isPrivate) {
        this.name = name;
        this.color = color;
        this.workspace = workspace;
        this.owner = owner;
        this.isPrivate = isPrivate;
    }
}

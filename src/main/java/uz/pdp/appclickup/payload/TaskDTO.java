package uz.pdp.appclickup.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {
    @NotNull
    private String name;

    private String description;

    private Long priorityId;

    private String priorityName;

    private UUID statusId;

    private String statusName;

    private String statusColor;

    private Timestamp startedDate;

    private Boolean startTimeHas;

    private Timestamp dueDate;

    private Boolean dueTimeHas;

    private Long estimateTime;

    private Set<UUID> userId;

    private List<UserDTO> userDTO;

    public TaskDTO(String name, String description, Long priorityId, String priorityName, String statusName, String statusColor, Timestamp startedDate, Timestamp dueDate, Long estimateTime, List<UserDTO> userDTO) {
        this.name = name;
        this.description = description;
        this.priorityId = priorityId;
        this.priorityName = priorityName;
        this.statusName = statusName;
        this.statusColor = statusColor;
        this.startedDate = startedDate;
        this.dueDate = dueDate;
        this.estimateTime = estimateTime;
        this.userDTO = userDTO;
    }
}

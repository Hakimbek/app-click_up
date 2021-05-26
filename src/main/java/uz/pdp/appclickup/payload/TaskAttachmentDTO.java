package uz.pdp.appclickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskAttachmentDTO {
    private UUID attachmentId;

    private boolean pinCoverImg;
}

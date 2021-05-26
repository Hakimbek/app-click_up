package uz.pdp.appclickup.service.task.service;

import uz.pdp.appclickup.entity.task.TaskAttachment;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskAttachmentDTO;

import java.util.List;
import java.util.UUID;

public interface TaskAttachmentService {
    ApiResponse addTaskAttachment(UUID taskId, TaskAttachmentDTO taskAttachmentDTO);

    ApiResponse deleteTaskAttachment(UUID taskAttachmentId);

    List<TaskAttachment> getAllTaskAttachments(UUID taskId);
}

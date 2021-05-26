package uz.pdp.appclickup.service.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Attachment;
import uz.pdp.appclickup.entity.task.Task;
import uz.pdp.appclickup.entity.task.TaskAttachment;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskAttachmentDTO;
import uz.pdp.appclickup.repository.AttachmentRepository;
import uz.pdp.appclickup.repository.TaskAttachmentRepository;
import uz.pdp.appclickup.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskAttachmentServiceImpl implements TaskAttachmentService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskAttachmentRepository taskAttachmentRepository;
    @Autowired
    AttachmentRepository attachmentRepository;


    /**
     * ADD TASK ATTACHMENT
     *
     * @param taskId            UUID
     * @param taskAttachmentDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addTaskAttachment(UUID taskId, TaskAttachmentDTO taskAttachmentDTO) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(taskAttachmentDTO.getAttachmentId());
        if (!optionalAttachment.isPresent()) {
            return new ApiResponse("Attachment not found", false);
        }
        Attachment attachment = optionalAttachment.get();

        taskAttachmentRepository.save(new TaskAttachment(
                task,
                attachment,
                taskAttachmentDTO.isPinCoverImg()
        ));

        return new ApiResponse("Successfully added", true);
    }


    /**
     * DELETE TASK ATTACHMENT
     *
     * @param taskAttachmentId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteTaskAttachment(UUID taskAttachmentId) {
        try {
            taskAttachmentRepository.deleteById(taskAttachmentId);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL TASK ATTACHMENT
     *
     * @param taskId UUID
     * @return TASK ATTACHMENT LIST
     */
    @Override
    public List<TaskAttachment> getAllTaskAttachments(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ArrayList<>();
        }

        return taskAttachmentRepository.findAllByTaskId(taskId);
    }
}

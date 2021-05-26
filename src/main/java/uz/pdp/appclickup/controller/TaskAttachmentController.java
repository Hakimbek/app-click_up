package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.TaskAttachment;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskAttachmentDTO;
import uz.pdp.appclickup.service.task.service.TaskAttachmentService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taskAttachment")
public class TaskAttachmentController {
    @Autowired
    TaskAttachmentService taskAttachmentService;


    /**
     * ADD TASK ATTACHMENT
     *
     * @param taskId            UUID
     * @param taskAttachmentDTO
     * @return API RESPONSE
     */
    @PostMapping("/{taskId}")
    public ResponseEntity<?> addTaskAttachment(@PathVariable UUID taskId,
                                               @Valid @RequestBody TaskAttachmentDTO taskAttachmentDTO) {
        ApiResponse apiResponse = taskAttachmentService.addTaskAttachment(taskId, taskAttachmentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE TASK ATTACHMENT
     *
     * @param taskAttachmentId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{taskAttachmentId}")
    public ResponseEntity<?> deleteTaskAttachment(@PathVariable UUID taskAttachmentId) {
        ApiResponse apiResponse = taskAttachmentService.deleteTaskAttachment(taskAttachmentId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL TASK ATTACHMENT
     *
     * @param taskId UUID
     * @return TASK ATTACHMENT LIST
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getAllTaskAttachments(@PathVariable UUID taskId) {
        List<TaskAttachment> taskAttachments = taskAttachmentService.getAllTaskAttachments(taskId);
        return ResponseEntity.status(taskAttachments.size() != 0 ? 200 : 409).body(taskAttachments);
    }
}

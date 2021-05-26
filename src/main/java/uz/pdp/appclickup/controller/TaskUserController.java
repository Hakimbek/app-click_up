package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.TaskUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.service.task.service.TaskUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taskUser")
public class TaskUserController {
    @Autowired
    TaskUserService taskUserService;


    /**
     * ADD TASK USER
     *
     * @param taskId UUID
     * @param userId UUID
     * @return API RESPONSE
     */
    @PostMapping("/{taskId}")
    public ResponseEntity<?> addTaskUser(@PathVariable UUID taskId, @RequestParam UUID userId) {
        ApiResponse apiResponse = taskUserService.addTaskUser(taskId, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE TASK USER
     *
     * @param taskUserId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{taskUserId}")
    public ResponseEntity<?> deleteTaskUser(@PathVariable UUID taskUserId) {
        ApiResponse apiResponse = taskUserService.deleteTaskUser(taskUserId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL TASK USERS
     *
     * @param taskId UUID
     * @return TASK USERS LIST
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getAllTaskUser(@PathVariable UUID taskId) {
        List<TaskUser> taskUsers = taskUserService.getAllTaskUser(taskId);
        return ResponseEntity.status(taskUsers.size() != 0 ? 200 : 409).body(taskUsers);
    }
}

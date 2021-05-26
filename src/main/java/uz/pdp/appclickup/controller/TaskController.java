package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskDTO;
import uz.pdp.appclickup.service.task.service.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;


    /**
     * ADD TASK
     *
     * @param categoryId UUID
     * @param taskDTO
     * @return API RESPONSE
     */
    @PostMapping("/{categoryId}")
    public ResponseEntity<?> addTask(@PathVariable UUID categoryId,
                                     @Valid @RequestBody TaskDTO taskDTO) {
        ApiResponse apiResponse = taskService.addTask(categoryId, taskDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE TASK
     *
     * @param taskId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID taskId) {
        ApiResponse apiResponse = taskService.deleteTask(taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL TASKS
     *
     * @param categoryId UUID
     * @return TASKS LIST
     */
    @GetMapping("/all/{categoryId}")
    public ResponseEntity<?> getAllTasks(@PathVariable UUID categoryId) {
        List<TaskDTO> tasks = taskService.getAllTasks(categoryId);
        return ResponseEntity.status(tasks.size() != 0 ? 200 : 409).body(tasks);
    }


    /**
     * GET ONE TASK
     *
     * @param taskId UUID
     * @return ONE ATSK
     */
    @GetMapping("/one/{taskId}")
    public ResponseEntity<?> getOneTask(@PathVariable UUID taskId) {
        TaskDTO task = taskService.getOneTask(taskId);
        return ResponseEntity.status(task != null ? 200 : 409).body(task);
    }


    /**
     * EDIT TASK
     *
     * @param taskId  UUID
     * @param taskDTO
     * @return API RESPONSE
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<?> editTask(@PathVariable UUID taskId,
                                     @Valid @RequestBody TaskDTO taskDTO) {
        ApiResponse apiResponse = taskService.editTask(taskId, taskDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

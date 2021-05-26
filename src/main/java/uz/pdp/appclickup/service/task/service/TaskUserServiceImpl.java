package uz.pdp.appclickup.service.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.task.Task;
import uz.pdp.appclickup.entity.task.TaskUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.repository.TaskRepository;
import uz.pdp.appclickup.repository.TaskUserRepository;
import uz.pdp.appclickup.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskUserServiceImpl implements TaskUserService {
    @Autowired
    TaskUserRepository taskUserRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * ADD TASK USER
     *
     * @param taskId UUID
     * @param userId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addTaskUser(UUID taskId, UUID userId) {
        if (taskUserRepository.existsByTaskIdAndUserId(taskId, userId)) {
            return new ApiResponse("User already exist", false);
        }

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();

        taskUserRepository.save(new TaskUser(
                task,
                user
        ));

        return new ApiResponse("User added", true);
    }


    /**
     * DELETE TASK USER
     *
     * @param taskUserId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteTaskUser(UUID taskUserId) {
        try {
            taskUserRepository.deleteById(taskUserId);
            return new ApiResponse("Task user deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL TASK USERS
     *
     * @param taskId UUID
     * @return TASK USERS LIST
     */
    @Override
    public List<TaskUser> getAllTaskUser(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ArrayList<>();
        }

        return taskUserRepository.findAllByTaskId(taskId);
    }
}

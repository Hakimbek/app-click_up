package uz.pdp.appclickup.service.task.service;

import uz.pdp.appclickup.entity.task.TaskUser;
import uz.pdp.appclickup.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface TaskUserService {
    ApiResponse addTaskUser(UUID taskId, UUID userId);

    ApiResponse deleteTaskUser(UUID taskUserId);

    List<TaskUser> getAllTaskUser(UUID taskId);
}

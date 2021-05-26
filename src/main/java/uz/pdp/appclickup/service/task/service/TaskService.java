package uz.pdp.appclickup.service.task.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    ApiResponse addTask(UUID categoryId, TaskDTO taskDTO);

    ApiResponse deleteTask(UUID taskId);

    List<TaskDTO> getAllTasks(UUID categoryId);

    TaskDTO getOneTask(UUID taskId);

    ApiResponse editTask(UUID taskId, TaskDTO taskDTO);
}

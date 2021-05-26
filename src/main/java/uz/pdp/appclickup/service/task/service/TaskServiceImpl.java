package uz.pdp.appclickup.service.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.enums.StatusType;
import uz.pdp.appclickup.entity.task.Checklist;
import uz.pdp.appclickup.entity.task.Priority;
import uz.pdp.appclickup.entity.task.Task;
import uz.pdp.appclickup.entity.task.TaskUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TaskDTO;
import uz.pdp.appclickup.payload.UserDTO;
import uz.pdp.appclickup.repository.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    PriorityRepository priorityRepository;
    @Autowired
    TaskUserRepository taskUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChecklistRepository checklistRepository;


    /**
     * ADD TASK
     *
     * @param categoryId UUID
     * @param taskDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addTask(UUID categoryId, TaskDTO taskDTO) {
        if (taskRepository.existsByNameAndCategoryId(taskDTO.getName(), categoryId)) {
            return new ApiResponse("Task already exist", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found", false);
        }
        Category category = optionalCategory.get();

        Status open = statusRepository.save(new Status(
                "TO DO",
                "white",
                null,
                null,
                category,
                StatusType.OPEN
        ));

        Status complete = statusRepository.save(new Status(
                "COMPLETE",
                "blue",
                null,
                null,
                category,
                StatusType.CLOSED
        ));

        Optional<Priority> optionalPriority = priorityRepository.findById(taskDTO.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Priority priority = optionalPriority.get();

        Task savedTask = taskRepository.save(new Task(
                taskDTO.getName(),
                taskDTO.getDescription(),
                category,
                open,
                priority,
                null,
                taskDTO.getStartedDate(),
                taskDTO.getStartTimeHas(),
                taskDTO.getDueDate(),
                taskDTO.getDueTimeHas(),
                taskDTO.getEstimateTime(),
                new Timestamp(System.currentTimeMillis())
        ));

        Set<UUID> userId = taskDTO.getUserId();
        List<User> users = new ArrayList<>();
        for (UUID uuid : userId) {
            if (taskUserRepository.existsByTaskIdAndUserId(savedTask.getId(), uuid)) {
                Optional<User> optionalUser = userRepository.findById(uuid);
                optionalUser.ifPresent(users::add);
            }
        }

        List<TaskUser> taskUsers = users.stream().map(user -> userAndTaskToTaskUser(user, savedTask)).collect(Collectors.toList());
        taskUserRepository.saveAll(taskUsers);

        checklistRepository.save(new Checklist(
                "CHECKLIST",
                savedTask
        ));

        return new ApiResponse("Task created", true);
    }


    /**
     * DELETE TASK
     *
     * @param taskId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteTask(UUID taskId) {
        try {
            taskRepository.deleteById(taskId);
            return new ApiResponse("Task deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL TASKS
     *
     * @param categoryId UUID
     * @return TASKS LIST
     */
    @Override
    public List<TaskDTO> getAllTasks(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return new ArrayList<>();
        }

        List<Task> allByCategoryId = taskRepository.findAllByCategoryId(categoryId);
        return allByCategoryId.stream().map(this::taskToTaskDTO).collect(Collectors.toList());
    }


    /**
     * GET ONE TASK
     *
     * @param taskId UUID
     * @return ONE ATSK
     */
    @Override
    public TaskDTO getOneTask(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.map(this::taskToTaskDTO).orElse(null);
    }


    /**
     * EDIT TASK
     *
     * @param taskId  UUID
     * @param taskDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editTask(UUID taskId, TaskDTO taskDTO) {
        if (taskRepository.existsByNameAndIdNot(taskDTO.getName(), taskId)) {
            return new ApiResponse("Task already exist", false);
        }

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }

        Optional<Priority> optionalPriority = priorityRepository.findById(taskDTO.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Priority priority = optionalPriority.get();

        Optional<Status> optionalStatus = statusRepository.findById(taskDTO.getStatusId());
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();

        Task task = optionalTask.get();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(priority);
        task.setStatus(status);
        task.setStartedDate(taskDTO.getStartedDate());
        task.setStartTimeHas(taskDTO.getStartTimeHas());
        task.setDueDate(taskDTO.getDueDate());
        task.setDueTimeHas(taskDTO.getDueTimeHas());
        task.setEstimateTime(taskDTO.getEstimateTime());
        taskRepository.save(task);

        return new ApiResponse("Task edited", true);
    }





    // My methods

    public TaskUser userAndTaskToTaskUser(User user, Task task) {
        return new TaskUser(
                task,
                user
        );
    }

    public UserDTO taskUserToUserDTO(TaskUser taskUser) {
        return new UserDTO(
                taskUser.getUser().getEmail(),
                taskUser.getUser().getInitialLetter(),
                taskUser.getUser().getColor()
        );
    }

    public TaskDTO taskToTaskDTO(Task task) {

        List<TaskUser> allByTaskId = taskUserRepository.findAllByTaskId(task.getId());
        List<UserDTO> usersDTO = allByTaskId.stream().map(this::taskUserToUserDTO).collect(Collectors.toList());

        return new TaskDTO(
                task.getName(),
                task.getDescription(),
                task.getPriority().getId(),
                task.getPriority().getName(),
                task.getStatus().getName(),
                task.getStatus().getColor(),
                task.getStartedDate(),
                task.getDueDate(),
                task.getEstimateTime(),
                usersDTO
        );
    }
}

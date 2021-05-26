package uz.pdp.appclickup.service.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.task.Checklist;
import uz.pdp.appclickup.entity.task.Task;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.repository.ChecklistRepository;
import uz.pdp.appclickup.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChecklistServiceImpl implements ChecklistService{
    @Autowired
    ChecklistRepository checklistRepository;
    @Autowired
    TaskRepository taskRepository;


    /**
     * ADD CHECKLIST
     *
     * @param taskId UUID
     * @param name
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addChecklist(UUID taskId, String name) {
        if (checklistRepository.existsByNameAndTaskId(name, taskId)) {
            return new ApiResponse("Checklist already exist", false);
        }

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();

        checklistRepository.save(new Checklist(
                name,
                task
        ));

        return new ApiResponse("Checklist created", true);
    }


    /**
     * DELETE CHECKLIST
     *
     * @param checklistId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteChecklist(UUID checklistId) {
        try {
            checklistRepository.deleteById(checklistId);
            return new ApiResponse("Checklist deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL CHECKLIST
     *
     * @param taskId UUID
     * @return CHECKLISTS LIST
     */
    @Override
    public List<Checklist> getAllChecklist(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ArrayList<>();
        }

        return checklistRepository.findAllByTaskId(taskId);
    }


    /**
     * EDIT CHECKLIST
     *
     * @param checklistId UUID
     * @param name
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editChecklist(UUID checklistId, String name) {
        if (checklistRepository.existsByNameAndTaskId(name, checklistId)) {
            return new ApiResponse("Checklist already exist", false);
        }

        Optional<Checklist> optionalChecklist = checklistRepository.findById(checklistId);
        if (!optionalChecklist.isPresent()) {
            return new ApiResponse("Checklist not found", false);
        }
        Checklist checklist = optionalChecklist.get();
        checklist.setName(name);
        checklistRepository.save(checklist);

        return new ApiResponse("Checklist edited", true);
    }
}

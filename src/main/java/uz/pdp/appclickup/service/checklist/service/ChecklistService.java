package uz.pdp.appclickup.service.checklist.service;

import uz.pdp.appclickup.entity.task.Checklist;
import uz.pdp.appclickup.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface ChecklistService {
    ApiResponse addChecklist(UUID taskId, String name);

    ApiResponse deleteChecklist(UUID checklistId);

    List<Checklist> getAllChecklist(UUID taskId);

    ApiResponse editChecklist(UUID checklistId, String name);
}

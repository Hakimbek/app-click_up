package uz.pdp.appclickup.service.checklist.service;

import uz.pdp.appclickup.entity.task.CheckListItem;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ChecklistItemDTO;

import java.util.List;
import java.util.UUID;

public interface ChecklistItemService {
    ApiResponse addChecklistItem(UUID checklistId, ChecklistItemDTO checklistItemDTO);

    ApiResponse deleteChecklistItem(UUID checklistItemId);

    List<CheckListItem> getAllChecklistItem(UUID checklistId);

    ApiResponse editChecklistItem(UUID checklistItemId, ChecklistItemDTO checklistItemDTO);
}

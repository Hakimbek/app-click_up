package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.CheckListItem;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ChecklistItemDTO;
import uz.pdp.appclickup.service.checklist.service.ChecklistItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklistItem")
public class ChecklistItemController {
    @Autowired
    ChecklistItemService checklistItemService;


    /**
     * ADD CHECKLIST ITEM
     *
     * @param checklistId      UUID
     * @param checklistItemDTO
     * @return API RESPONSE
     */
    @PostMapping("/{checklistId}")
    public ResponseEntity<?> addChecklistItem(@PathVariable UUID checklistId,
                                              @Valid @RequestBody ChecklistItemDTO checklistItemDTO) {
        ApiResponse apiResponse = checklistItemService.addChecklistItem(checklistId, checklistItemDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE CHECKLIST ITEM
     *
     * @param checklistItemId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{checklistItemId}")
    public ResponseEntity<?> deleteChecklistItem(@PathVariable UUID checklistItemId) {
        ApiResponse apiResponse = checklistItemService.deleteChecklistItem(checklistItemId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL CHECKLISTS ITEM
     *
     * @param checklistId UUID
     * @return CHECKLIST ITEMS LIST
     */
    @GetMapping("/{checklistId}")
    public ResponseEntity<?> getAllChecklistItem(@PathVariable UUID checklistId) {
        List<CheckListItem> checkListItems = checklistItemService.getAllChecklistItem(checklistId);
        return ResponseEntity.status(checkListItems.size() != 0 ? 200 : 409).body(checkListItems);
    }


    /**
     * EDIT CHECKLIST ITEM
     *
     * @param checklistItemId  UUID
     * @param checklistItemDTO
     * @return API RESPONSE
     */
    @PutMapping("/{checklistItemId}")
    public ResponseEntity<?> editChecklistItem(@PathVariable UUID checklistItemId,
                                               @Valid @RequestBody ChecklistItemDTO checklistItemDTO) {
        ApiResponse apiResponse = checklistItemService.editChecklistItem(checklistItemId, checklistItemDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

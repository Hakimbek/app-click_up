package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.Checklist;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.service.checklist.service.ChecklistService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {
    @Autowired
    ChecklistService checklistService;


    /**
     * ADD CHECKLIST
     *
     * @param taskId UUID
     * @param name
     * @return API RESPONSE
     */
    @PostMapping("/{taskId}")
    public ResponseEntity<?> addChecklist(@PathVariable UUID taskId, @RequestParam String name) {
        ApiResponse apiResponse = checklistService.addChecklist(taskId, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE CHECKLIST
     *
     * @param checklistId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<?> deleteChecklist(@PathVariable UUID checklistId) {
        ApiResponse apiResponse = checklistService.deleteChecklist(checklistId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL CHECKLIST
     *
     * @param taskId UUID
     * @return CHECKLISTS LIST
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getAllChecklist(@PathVariable UUID taskId) {
        List<Checklist> checklists = checklistService.getAllChecklist(taskId);
        return ResponseEntity.status(checklists.size() != 0 ? 200 : 409).body(checklists);
    }


    /**
     * EDIT CHECKLIST
     *
     * @param checklistId UUID
     * @param name
     * @return API RESPONSE
     */
    @PutMapping("/{checklistId}")
    public ResponseEntity<?> editChecklist(@PathVariable UUID checklistId, @RequestParam String name) {
        ApiResponse apiResponse = checklistService.editChecklist(checklistId, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.StatusDTO;
import uz.pdp.appclickup.service.status.service.StatusService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    StatusService statusService;


    /**
     * ADD STATUS
     *
     * @param statusDTO
     * @return API RESPONSE
     */
    @PostMapping
    public ResponseEntity<?> addStatus(@Valid @RequestBody StatusDTO statusDTO) {
        ApiResponse apiResponse = statusService.addStatus(statusDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE STATUS
     *
     * @param statusId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{statusId}")
    public ResponseEntity<?> deleteStatus(@PathVariable UUID statusId) {
        ApiResponse apiResponse = statusService.deleteStatus(statusId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL STATUS BY SPACE ID
     *
     * @param spaceId UUID
     * @return STATUS LIST
     */
    @GetMapping("space/{spaceId}")
    public ResponseEntity<?> getAllSpaceStatus(@PathVariable UUID spaceId) {
        List<Status> status = statusService.getAllSpaceStatus(spaceId);
        return ResponseEntity.status(status.size() != 0 ? 200 : 409).body(status);
    }


    /**
     * GET ALL STATUS BY PROJECT ID
     *
     * @param projectId UUID
     * @return STATUS LIST
     */
    @GetMapping("project/{projectId}")
    public ResponseEntity<?> getAllProjectStatus(@PathVariable UUID projectId) {
        List<Status> status = statusService.getAllProjectStatus(projectId);
        return ResponseEntity.status(status.size() != 0 ? 200 : 409).body(status);
    }


    /**
     * GET ALL STATUS BY CATEGORY ID
     *
     * @param categoryId UUID
     * @return STATUS LIST
     */
    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getAllCategoryStatus(@PathVariable UUID categoryId) {
        List<Status> status = statusService.getAllCategoryStatus(categoryId);
        return ResponseEntity.status(status.size() != 0 ? 200 : 409).body(status);
    }


    /**
     * EDIT STATUS
     *
     * @param statusId  UUID
     * @param statusDTO
     * @return API RESPONSE
     */
    @PutMapping("/{statusId}")
    public ResponseEntity<?> editStatus(@PathVariable UUID statusId,
                                        @Valid @RequestBody StatusDTO statusDTO) {
        ApiResponse apiResponse = statusService.editStatus(statusId, statusDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ONE STATUS
     *
     * @param statusId UUID
     * @return STATUS
     */
    @GetMapping("/one/{statusId}")
    public ResponseEntity<?> getOneStatus(@PathVariable UUID statusId) {
        Status status = statusService.getOneStatus(statusId);
        return ResponseEntity.status(status != null ? 200 : 409).body(status);
    }
}

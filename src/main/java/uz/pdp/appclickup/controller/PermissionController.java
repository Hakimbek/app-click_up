package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionDTO;
import uz.pdp.appclickup.service.permission.service.PermissionService;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    /**
     * ADD OR REMOVE PERMISSION
     *
     * @param permissionDTO ROLE ID, PERMISSION LIST
     * @return Api Response
     */
    @PutMapping("/addOrRemovePermission")
    public ResponseEntity<?> addOrRemovePermission(@RequestBody PermissionDTO permissionDTO) {
        ApiResponse apiResponse = permissionService.addOrRemovePermission(permissionDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

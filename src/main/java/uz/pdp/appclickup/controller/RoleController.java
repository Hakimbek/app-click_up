package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.RoleDTO;
import uz.pdp.appclickup.service.role.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * ADD ROLE
     *
     * @param id      LONG
     * @param roleDTO ROLE NAME, PERMISSION LIST
     * @return Api Response
     */
    @PostMapping("/addRole/{id}")
    public ResponseEntity<?> addRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        ApiResponse apiResponse = roleService.addRole(id, roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

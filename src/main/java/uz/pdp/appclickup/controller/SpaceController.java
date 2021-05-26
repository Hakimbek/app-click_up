package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDTO;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.space.service.SpaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;


    /**
     * ADD SPACE
     *
     * @param id       LONG
     * @param spaceDTO
     * @param user
     * @return Api Responce
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> addSpace(@PathVariable Long id,
                                      @RequestBody SpaceDTO spaceDTO,
                                      @CurrentUser User user) {
        ApiResponse apiResponse = spaceService.addSpace(id, spaceDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE SPACE
     *
     * @param id UUID
     * @return Api Response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.deleteSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET SPACES LIST
     *
     * @param id LONG
     * @return Api Response
     */
    @GetMapping("workspaceId/{id}")
    public ResponseEntity<?> getSpaces(@PathVariable Long id) {
        List<Space> spaces = spaceService.getSpaces(id);
        return ResponseEntity.status(spaces.size() != 0 ? 200 : 409).body(spaces);
    }


    /**
     * GET ONE SPACE
     *
     * @param id LONG
     * @return Api Response
     */
    @GetMapping("spaceId/{id}")
    public ResponseEntity<?> getOneSpace(@PathVariable UUID id) {
        Space spaces = spaceService.getOneSpace(id);
        return ResponseEntity.status(spaces != null ? 200 : 409).body(spaces);
    }


    /**
     * EDIT SPACE, ADD OR REMOVE SPACE USER
     * @param id UUID
     * @param spaceDTO
     * @return Api Response
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editSpace(@PathVariable UUID id, @RequestBody SpaceDTO spaceDTO) {
        ApiResponse apiResponse = spaceService.editSpace(id, spaceDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

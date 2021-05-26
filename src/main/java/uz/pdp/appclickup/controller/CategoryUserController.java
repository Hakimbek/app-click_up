package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryUserDTO;
import uz.pdp.appclickup.service.category.sevice.CategoryUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categoryUser")
public class CategoryUserController {
    @Autowired
    CategoryUserService categoryUserService;


    /**
     * ADD CATEGORY USER
     * @param categoryUserDTO CATEGORY ID, USER ID, PERMISSION
     * @return API RESPONSE
     */
    @PostMapping("/{categoryId}")
    public ResponseEntity<?> addCategoryUser(@PathVariable UUID categoryId,
                                            @Valid @RequestBody CategoryUserDTO categoryUserDTO) {
        ApiResponse apiResponse = categoryUserService.addCategoryUser(categoryId, categoryUserDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE CATEGORY USER
     *
     * @param categoryUserId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{categoryUserId}")
    public ResponseEntity<?> deleteCategoryUser(@PathVariable UUID categoryUserId) {
        ApiResponse apiResponse = categoryUserService.deleteCategoryUser(categoryUserId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL CATEGORY USERS
     *
     * @param categoryId UUID
     * @return CATEGORY USER LIST
     */
    @GetMapping("/all/{categoryId}")
    public ResponseEntity<?> getAllCategoryUser(@PathVariable UUID categoryId) {
        List<CategoryUserDTO> categoryUsers = categoryUserService.getAllCategory(categoryId);
        return ResponseEntity.status(categoryUsers.size() != 0 ? 200 : 409).body(categoryUsers);
    }


    /**
     * GET ONE CATEGORY USER DTO
     *
     * @param categoryUserId UUID
     * @return ONE CATEGORY DTO
     */
    @GetMapping("/one/{categoryUserId}")
    public ResponseEntity<?> getOneCategoryUser(@PathVariable UUID categoryUserId) {
        CategoryUserDTO categoryUserDTO = categoryUserService.getOneCategory(categoryUserId);
        return ResponseEntity.status(categoryUserDTO != null ? 200 : 409).body(categoryUserDTO);
    }


    /**
     * EDIT CATEGORY USER
     *
     * @param categoryUserId  UUID
     * @param categoryUserDTO
     * @return API RESPONSE
     */
    @PutMapping("/{categoryUserId}")
    public ResponseEntity<?> editCategoryUser(@PathVariable UUID categoryUserId,
                                              @Valid @RequestBody CategoryUserDTO categoryUserDTO) {
        ApiResponse apiResponse = categoryUserService.editCategoryUser(categoryUserId, categoryUserDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

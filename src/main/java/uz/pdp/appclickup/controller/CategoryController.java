package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryDTO;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.category.sevice.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    /**
     * ADD CATEGORY
     *
     * @param projectId   UUID
     * @param categoryDTO NAME, PROJECT ID, IS PRIVATE, ARCHIVED, COLOR
     * @return API RESPONSE
     */
    @PostMapping("/addCategoryInProject/{projectId}")
    public ResponseEntity<?> addCategory(@PathVariable UUID projectId,
                                         @Valid @RequestBody CategoryDTO categoryDTO,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = categoryService.addCategoryInProject(projectId, categoryDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE CATEGORY
     *
     * @param categoryId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId) {
        ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL CATEGORY BY PROJECT ID
     *
     * @param projectId UUID
     * @return CATEGORY LIST
     */
    @GetMapping("/all/{projectId}")
    public ResponseEntity<?> getAllCategory(@PathVariable UUID projectId) {
        List<Category> categories = categoryService.getAllCategory(projectId);
        return ResponseEntity.status(categories.size() != 0 ? 200 : 409).body(categories);
    }


    /**
     * GET ONE CATEGORY BY CATEGORY ID
     *
     * @param categoryId UUI
     * @return ONE CATEGORY
     */
    @GetMapping("/one/{categoryId}")
    public ResponseEntity<?> getOneCategory(@PathVariable UUID categoryId) {
        Category category = categoryService.getOneCategory(categoryId);
        return ResponseEntity.status(category != null ? 200 : 409).body(category);
    }


    /**
     * EDIT CATEGORY
     *
     * @param categoryId UUID
     * @param categoryDTO NAME, IS PRIVATE, ARCHIVED, COLOR
     * @return API RESPONSE
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> editCategory(@PathVariable UUID categoryId,
                                          @Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.editCategory(categoryId, categoryDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

package uz.pdp.appclickup.service.category.sevice;

import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    ApiResponse addCategoryInProject(UUID projectId, CategoryDTO categoryDTO, User user);

    ApiResponse deleteCategory(UUID categoryId);

    List<Category> getAllCategory(UUID projectId);

    Category getOneCategory(UUID categoryId);

    ApiResponse editCategory(UUID categoryId, CategoryDTO categoryDTO);
}

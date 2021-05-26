package uz.pdp.appclickup.service.category.sevice;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryUserDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryUserService {
    ApiResponse addCategoryUser(UUID categoryId, CategoryUserDTO categoryUserDTO);

    ApiResponse deleteCategoryUser(UUID categoryUserId);

    List<CategoryUserDTO> getAllCategory(UUID categoryId);

    CategoryUserDTO getOneCategory(UUID categoryUserId);

    ApiResponse editCategoryUser(UUID categoryUserId, CategoryUserDTO categoryUserDTO);
}

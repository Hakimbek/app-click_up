package uz.pdp.appclickup.service.category.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.category.CategoryUser;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryUserDTO;
import uz.pdp.appclickup.repository.CategoryRepository;
import uz.pdp.appclickup.repository.CategoryUserRepository;
import uz.pdp.appclickup.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryUserServiceImpl implements CategoryUserService {
    @Autowired
    CategoryUserRepository categoryUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;


    /**
     * ADD CATEGORY USER
     *
     * @param categoryUserDTO CATEGORY ID, USER ID, PERMISSION
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addCategoryUser(UUID categoryId, CategoryUserDTO categoryUserDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Ctegory not found", false);
        }
        Category category = optionalCategory.get();

        Optional<User> optionalUser = userRepository.findById(categoryUserDTO.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();

        if (!categoryUserRepository.existsByCategoryIdAndUserId(category.getId(), user.getId())) {
            categoryUserRepository.save(new CategoryUser(
                    category,
                    user,
                    categoryUserDTO.getPermissionNames()
            ));

            return new ApiResponse("CategoryUser added", true);
        }

        return new ApiResponse("CategoryUser already added", false);
    }


    /**
     * DELETE CATEGORY USER
     *
     * @param categoryUserId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteCategoryUser(UUID categoryUserId) {
        try {
            categoryUserRepository.deleteById(categoryUserId);
            return new ApiResponse("CategoryUser deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting CategoryUser", false);
        }
    }


    /**
     * GET ALL CATEGORY USERS
     *
     * @param categoryId UUID
     * @return CATEGORY USER LIST
     */
    @Override
    public List<CategoryUserDTO> getAllCategory(UUID categoryId) {
        Optional<CategoryUser> optionalCategoryUser = categoryUserRepository.findById(categoryId);
        if (!optionalCategoryUser.isPresent()) {
            return new ArrayList<>();
        }

        List<CategoryUser> allByCategoryId = categoryUserRepository.findAllByCategoryId(categoryId);
        return allByCategoryId.stream().map(this::categoryUserDTOForFrontend).collect(Collectors.toList());
    }


    /**
     * GET ONE CATEGORY USER DTO
     *
     * @param categoryUserId UUID
     * @return ONE CATEGORY DTO
     */
    @Override
    public CategoryUserDTO getOneCategory(UUID categoryUserId) {
        Optional<CategoryUser> optionalCategoryUser = categoryUserRepository.findById(categoryUserId);
        if (!optionalCategoryUser.isPresent()) {
            return null;
        }
        CategoryUser categoryUser = optionalCategoryUser.get();

        return new CategoryUserDTO(
                categoryUser.getUser().getId(),
                categoryUser.getUser().getEmail(),
                categoryUser.getPermissionNames()
        );
    }


    /**
     * EDIT CATEGORY USER
     *
     * @param categoryUserId  UUID
     * @param categoryUserDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editCategoryUser(UUID categoryUserId, CategoryUserDTO categoryUserDTO) {
        Optional<CategoryUser> optionalCategoryUser = categoryUserRepository.findById(categoryUserId);
        if (!optionalCategoryUser.isPresent()) {
            return new ApiResponse("CategoryUser not found", false);
        }
        CategoryUser categoryUser = optionalCategoryUser.get();
        categoryUser.setPermissionNames(categoryUserDTO.getPermissionNames());
        categoryUserRepository.save(categoryUser);

        return new ApiResponse("Category User edited", true);
    }


    // My methods

    public CategoryUserDTO categoryUserDTOForFrontend(CategoryUser categoryUser) {
        return new CategoryUserDTO(
                categoryUser.getUser().getId(),
                categoryUser.getUser().getEmail(),
                categoryUser.getPermissionNames()
        );
    }
}

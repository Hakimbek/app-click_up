package uz.pdp.appclickup.service.category.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.category.CategoryUser;
import uz.pdp.appclickup.entity.enums.ProjectPermissionName;
import uz.pdp.appclickup.entity.enums.StatusType;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryDTO;
import uz.pdp.appclickup.repository.CategoryRepository;
import uz.pdp.appclickup.repository.CategoryUserRepository;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.StatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CategoryUserRepository categoryUserRepository;
    @Autowired
    StatusRepository statusRepository;


    /**
     * ADD CATEGORY
     *
     * @param projectId   UUID
     * @param categoryDTO NAME, PROJECT ID, IS PRIVATE, ARCHIVED, COLOR
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addCategoryInProject(UUID projectId, CategoryDTO categoryDTO, User user) {
        // shunday nomli category yoqlogini tekshirdim
        if (categoryRepository.existsByNameAndProjectId(categoryDTO.getName(), projectId)) {
            return new ApiResponse("Category already exist", false);
        }

        // projectni id bo'yicha oldim
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();

        // category ni saqladim
        Category savedCategory = categoryRepository.save(new Category(
                categoryDTO.getName(),
                project,
                categoryDTO.getIsPrivate(),
                categoryDTO.getArchived(),
                categoryDTO.getColor()
        ));


        // agr category private bo'lsa
        // systemadagi userni CategoryUser ga projectPermissionName ni full qilib saqladim
        if (savedCategory.getIsPrivate().equals(true)) {
            categoryUserRepository.save(new CategoryUser(
                    savedCategory,
                    user,
                    ProjectPermissionName.FULL
            ));
        }

        // to do statusini qoshdim
        Status open = new Status(
                "TO DO",
                "white",
                null,
                null,
                savedCategory,
                StatusType.OPEN
        );
        statusRepository.save(open);

        // complete statusini qoshdim
        Status complete = new Status(
                "COMPLETE",
                "blue",
                null,
                null,
                savedCategory,
                StatusType.CLOSED
        );
        statusRepository.save(complete);

        return new ApiResponse("Category successfully created", true);
    }


    /**
     * DELETE CATEGORY
     *
     * @param categoryId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteCategory(UUID categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return new ApiResponse("Category deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting category", false);
        }
    }


    /**
     * GET ALL CATEGORY BY PROJECT ID
     *
     * @param projectId UUID
     * @return CATEGORY LIST
     */
    @Override
    public List<Category> getAllCategory(UUID projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            return new ArrayList<>();
        }

        return categoryRepository.findAllByProjectId(projectId);
    }


    /**
     * GET ONE CATEGORY BY CATEGORY ID
     *
     * @param categoryId UUI
     * @return ONE CATEGORY
     */
    @Override
    public Category getOneCategory(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null);
    }


    /**
     * EDIT CATEGORY
     *
     * @param categoryId UUID
     * @param categoryDTO NAME, IS PRIVATE, ARCHIVED, COLOR
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editCategory(UUID categoryId, CategoryDTO categoryDTO) {
        // shunday nomli category yoqligini tekshirdim
        if (categoryRepository.existsByNameAndIdNot(categoryDTO.getName(), categoryId)) {
            return new ApiResponse("Category already exist", false);
        }

        // category ni id bo'yicha oldim va edit qildim
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found", false);
        }
        Category category = optionalCategory.get();
        category.setName(categoryDTO.getName());
        category.setIsPrivate(categoryDTO.getIsPrivate());
        category.setArchived(categoryDTO.getArchived());
        category.setColor(categoryDTO.getColor());
        categoryRepository.save(category);

        return new ApiResponse("Category edited", true);
    }
}

package uz.pdp.appclickup.service.status.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.entity.category.Category;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.StatusDTO;
import uz.pdp.appclickup.repository.CategoryRepository;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.SpaceRepository;
import uz.pdp.appclickup.repository.StatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CategoryRepository categoryRepository;


    /**
     * ADD STATUS
     *
     * @param statusDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addStatus(StatusDTO statusDTO) {
        if (statusRepository.existsByName(statusDTO.getName())) {
            return new ApiResponse("Status already exist", false);
        }

        Space space = null;
        Project project = null;
        Category category = null;

        if (statusDTO.getSpaceId() != null) {
            Optional<Space> optionalSpace = spaceRepository.findById(statusDTO.getSpaceId());
            if (!optionalSpace.isPresent()) {
                return new ApiResponse("Space not found", false);
            }

            space = optionalSpace.get();
        }

        if (statusDTO.getProjectId() != null) {
            Optional<Project> optionalProject = projectRepository.findById(statusDTO.getProjectId());
            if (!optionalProject.isPresent()) {
                return new ApiResponse("Project not found", false);
            }

            project = optionalProject.get();
        }

        if (statusDTO.getCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(statusDTO.getCategoryId());
            if (!optionalCategory.isPresent()) {
                return new ApiResponse("Category not found", false);
            }

            category = optionalCategory.get();
        }

        statusRepository.save(new Status(
                statusDTO.getName(),
                statusDTO.getColor(),
                space,
                project,
                category,
                statusDTO.getStatusType()
        ));

        return new ApiResponse("Successfully created", true);
    }


    /**
     * DELETE STATUS
     *
     * @param statusId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteStatus(UUID statusId) {
        try {
            statusRepository.deleteById(statusId);
            return new ApiResponse("Status deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL STATUS BY SPACE ID
     *
     * @param spaceId UUID
     * @return STATUS LIST
     */
    @Override
    public List<Status> getAllSpaceStatus(UUID spaceId) {
        Optional<Space> optionalSpace = spaceRepository.findById(spaceId);
        if (!optionalSpace.isPresent()) {
            return new ArrayList<>();
        }

        return statusRepository.findAllBySpaceId(spaceId);
    }


    /**
     * GET ALL STATUS BY PROJECT ID
     *
     * @param projectId UUID
     * @return STATUS LIST
     */
    @Override
    public List<Status> getAllProjectStatus(UUID projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            return new ArrayList<>();
        }

        return statusRepository.findAllByProjectId(projectId);
    }


    /**
     * GET ALL STATUS BY CATEGORY ID
     *
     * @param categoryId UUID
     * @return STATUS LIST
     */
    @Override
    public List<Status> getAllCategoryStatus(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return new ArrayList<>();
        }

        return statusRepository.findAllByCategoryId(categoryId);
    }


    /**
     * EDIT STATUS
     *
     * @param statusId  UUID
     * @param statusDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editStatus(UUID statusId, StatusDTO statusDTO) {
        if (statusRepository.existsByNameAndIdNot(statusDTO.getName(), statusId)) {
            return new ApiResponse("Status already exist", false);
        }

        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();
        status.setName(statusDTO.getName());
        status.setColor(statusDTO.getColor());
        statusRepository.save(status);

        return new ApiResponse("Status edited", true);
    }


    /**
     * GET ONE STATUS
     *
     * @param statusId UUID
     * @return STATUS
     */
    @Override
    public Status getOneStatus(UUID statusId) {
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        return optionalStatus.orElse(null);
    }
}

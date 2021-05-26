package uz.pdp.appclickup.service.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.enums.ProjectPermissionName;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.entity.project.ProjectUser;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDTO;
import uz.pdp.appclickup.payload.ProjectUserDTO;
import uz.pdp.appclickup.repository.ProjectRepository;
import uz.pdp.appclickup.repository.ProjectUserRepository;
import uz.pdp.appclickup.repository.SpaceRepository;
import uz.pdp.appclickup.repository.UserRepository;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    ProjectUserRepository projectUserRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * ADD PROJECT
     *
     * @param id         LONG
     * @param projectDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addProject(UUID id, ProjectDTO projectDTO, User user) {
        if (projectRepository.existsByNameAndSpaceId(projectDTO.getName(), id)) {
            return new ApiResponse("Project already exist", false);
        }

        Optional<Space> optionalSpace = spaceRepository.findById(projectDTO.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();

        Project savedProject = projectRepository.save(new Project(
                projectDTO.getName(),
                space,
                projectDTO.isPrivate(),
                projectDTO.isArchived(),
                projectDTO.getColor()
        ));

        projectUserRepository.save(new ProjectUser(
                savedProject,
                user,
                ProjectPermissionName.FULL
        ));

        return new ApiResponse("Success", true);
    }


    /**
     * DELETE PROJECT
     *
     * @param id
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteProject(UUID id) {
        try {
            projectRepository.deleteById(id);
            return new ApiResponse("Success", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET PROJECTS LIST
     *
     * @param id
     * @return API RESPONSE
     */
    @Override
    public List<Project> getProjects(UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ArrayList<>();
        }
        Space space = optionalSpace.get();
        return projectRepository.findAllBySpaceId(space.getId());
    }


    /**
     * GET ONE PROJECT
     *
     * @param id
     * @return API RESPONSE
     */
    @Override
    public Project getOneProject(UUID id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElse(null);
    }


    /**
     * EDIT PROJECT
     *
     * @param id         UUID
     * @param projectDTO
     * @return API RESPONCE
     */
    @Override
    public ApiResponse editProject(UUID id, ProjectDTO projectDTO) {
        if (projectRepository.existsByNameAndIdNot(projectDTO.getName(), id)) {
            return new ApiResponse("Project already exist", false);
        }

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();
        project.setName(projectDTO.getName());
        project.setAccessType(projectDTO.isPrivate());
        project.setArchived(projectDTO.isArchived());
        project.setColor(projectDTO.getColor());
        projectRepository.save(project);
        return new ApiResponse("Success", true);
    }


    /**
     * ADD PROJECT USER
     *
     * @param id             UUID
     * @param projectUserDTO
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addProjectUser(UUID id, ProjectUserDTO projectUserDTO) {
        if (projectUserRepository.existsByProjectIdAndUserId(id, projectUserDTO.getUserId())) {
            return new ApiResponse("User already exist in this project", false);
        }

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();

        Optional<User> optionalUser = userRepository.findById(projectUserDTO.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();

        projectUserRepository.save(new ProjectUser(
                project,
                user,
                projectUserDTO.getProjectPermissionName()
        ));
        return new ApiResponse("Success", true);
    }


    /**
     * DELETE PROJECT USER
     *
     * @param id UUID
     * @param userId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteProjectUser(UUID id, UUID userId) {
        try {
            projectUserRepository.deleteByProjectIdAndUserId(id, userId);
            return new ApiResponse("Success", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}

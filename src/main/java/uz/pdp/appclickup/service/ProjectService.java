package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDTO;
import uz.pdp.appclickup.payload.ProjectUserDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ApiResponse addProject(UUID id, ProjectDTO projectDTO, User user);

    ApiResponse deleteProject(UUID id);

    List<Project> getProjects(UUID id);

    Project getOneProject(UUID id);

    ApiResponse editProject(UUID id, ProjectDTO projectDTO);

    ApiResponse addProjectUser(UUID id, ProjectUserDTO projectUserDTO);

    ApiResponse deleteProjectUser(UUID id, UUID userId);
}

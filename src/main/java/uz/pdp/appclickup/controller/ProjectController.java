package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.project.Project;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDTO;
import uz.pdp.appclickup.payload.ProjectUserDTO;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;


    /**
     * ADD PROJECT
     *
     * @param id         LONG
     * @param projectDTO
     * @return API RESPONSE
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> addProject(@PathVariable UUID id,
                                        @RequestBody ProjectDTO projectDTO,
                                        @CurrentUser User user) {
        ApiResponse apiResponse = projectService.addProject(id, projectDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE PROJECT
     *
     * @param id UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID id) {
        ApiResponse apiResponse = projectService.deleteProject(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET PROJECTS LIST
     *
     * @param id UUID
     * @return API RESPONSE
     */
    @GetMapping("spaceId/{id}")
    public ResponseEntity<?> getProjects(@PathVariable UUID id) {
        List<Project> projects = projectService.getProjects(id);
        return ResponseEntity.status(projects.size() != 0 ? 200 : 409).body(projects);
    }


    /**
     * GET ONE PROJECT
     *
     * @param id UUID
     * @return API RESPONSE
     */
    @GetMapping("projectId/{id}")
    public ResponseEntity<?> getOneProject(@PathVariable UUID id) {
        Project project = projectService.getOneProject(id);
        return ResponseEntity.status(project != null ? 200 : 409).body(project);
    }


    /**
     * EDIT PROJECT
     *
     * @param id         UUID
     * @param projectDTO
     * @return API RESPONCE
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editProject(@PathVariable UUID id, @RequestBody ProjectDTO projectDTO) {
        ApiResponse apiResponse = projectService.editProject(id, projectDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * ADD PROJECT USER
     *
     * @param id             UUID
     * @param projectUserDTO
     * @return API RESPONSE
     */
    @PostMapping("/addProjectUser/{id}")
    public ResponseEntity<?> addProjectUser(@PathVariable UUID id,
                                            @RequestBody ProjectUserDTO projectUserDTO) {
        ApiResponse apiResponse = projectService.addProjectUser(id, projectUserDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE PROJECT USER
     *
     * @param id UUID
     * @param userId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("deleteProjectUser/{id}")
    public ResponseEntity<?> deleteProjectUser(@PathVariable UUID id, @RequestParam UUID userId) {
        ApiResponse apiResponse = projectService.deleteProjectUser(id, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

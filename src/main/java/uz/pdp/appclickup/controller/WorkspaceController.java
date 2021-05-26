package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.entity.workspace.WorkspaceUser;
import uz.pdp.appclickup.payload.*;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.workspace.service.WorkspaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;


    /**
     * WORKSPACE QOSHISH
     *
     * @param workspaceDTO NAME, COLOR, AVATAR ID
     * @param user         USER
     * @return Api Response
     */
    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDTO workspaceDTO, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * NAME, COLOR, AVATAR O'ZGARAISHI MUMKIN
     *
     * @param id           LONG
     * @param workspaceDTO NAME, COLOR, AVATAR ID
     * @return Api Response
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDTO workspaceDTO) {
        ApiResponse apiResponse = workspaceService.editWorkspace(id, workspaceDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * OWNERNI O'ZGARTIRISH
     *
     * @param id         LONG
     * @param newOwnerId UUID
     * @return Api Response
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                              @RequestParam UUID newOwnerId, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, newOwnerId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL MEMBERS
     *
     * @param id LONG
     * @return WORKSPACE USER LIST
     */
    @GetMapping("/getMember/{id}")
    public ResponseEntity<?> getMembers(@PathVariable Long id) {
        List<WorkspaceUser> members = workspaceService.getMembers(id);
        return ResponseEntity.status(members.size() != 0 ? 200 : 409).body(members);
    }


    /**
     * GET ALL GUESTS
     *
     * @param id LONG
     * @return WORKSPACE USER LIST
     */
    @GetMapping("/getGuest/{id}")
    public ResponseEntity<?> getGuests(@PathVariable Long id) {
        List<WorkspaceUser> guests = workspaceService.getGuests(id);
        return ResponseEntity.status(guests.size() != 0 ? 200 : 409).body(guests);
    }


    /**
     * GET ALL WORKSPACES
     *
     * @return Api Response
     */
    @GetMapping("/getWorkspace")
    public ResponseEntity<?> getWorkspaces(@CurrentUser User user) {
        List<Workspace> workspaces = workspaceService.getWorkspaces(user);
        return ResponseEntity.status(workspaces.size() != 0 ? 200 : 409).body(workspaces);
    }


    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id LONG
     * @return Api Response
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * ADD OR EDIT OR REMOVE WORKSPACE USER
     *
     * @param id        LONG
     * @param memberDTO USER ID, ROLE ID, TYPE
     * @return Api Responce
     */
    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                    @RequestBody MemberDTO memberDTO) {
        ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, memberDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * JOIN
     *
     * @param id   LONG
     * @param user USER
     * @return Api Responce
     */
    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

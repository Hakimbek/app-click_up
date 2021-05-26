package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.Tag;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDTO;
import uz.pdp.appclickup.service.tag.service.TagService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;


    /**
     * ADD TAG
     *
     * @param workspaceId LONG
     * @param tagDTO      NAME, COLOR
     * @return API RESPONSE
     */
    @PostMapping("/{workspaceId}")
    public ResponseEntity<?> addTag(@PathVariable Long workspaceId,
                                    @Valid @RequestBody TagDTO tagDTO) {
        ApiResponse apiResponse = tagService.addTag(workspaceId, tagDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE TAG
     *
     * @param tagId UUID
     * @return API RESSPONSE
     */
    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable UUID tagId) {
        ApiResponse apiResponse = tagService.deleteTag(tagId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL TAGS
     *
     * @param workspaceId LONG
     * @return TAGS LIST
     */
    @GetMapping("all/{workspaceId}")
    public ResponseEntity<?> getAllTags(@PathVariable Long workspaceId) {
        List<Tag> tags = tagService.getAllTags(workspaceId);
        return ResponseEntity.status(tags.size() != 0 ? 200 : 409).body(tags);
    }


    /**
     * GET ONE TAG
     *
     * @param tagId UUID
     * @return TAG
     */
    @GetMapping("/one/{tagId}")
    public ResponseEntity<?> getOneTag(@PathVariable UUID tagId) {
        Tag tag = tagService.getOneTag(tagId);
        return ResponseEntity.status(tag != null ? 200 : 409).body(tag);
    }


    /**
     * EDIT TAG
     *
     * @param tagId  UUID
     * @param tagDTO NAME, COLOR
     * @return API RESPONSE
     */
    @PutMapping("/{tagId}")
    public ResponseEntity<?> editTag(@PathVariable UUID tagId,
                                    @Valid @RequestBody TagDTO tagDTO) {
        ApiResponse apiResponse = tagService.editTag(tagId, tagDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

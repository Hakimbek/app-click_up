package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.task.Comment;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CommentDTO;
import uz.pdp.appclickup.service.comment.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;


    /**
     * ADD COMMENT
     *
     * @param taskId     UUID
     * @param commentDTO TEXT
     * @return API RESPONSE
     */
    @PostMapping("/{taskId}")
    public ResponseEntity<?> addComment(@PathVariable UUID taskId,
                                        @Valid @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.addComment(taskId, commentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * DELETE COMMENT
     *
     * @param commentId UUID
     * @return API RESPONSE
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID commentId) {
        ApiResponse apiResponse = commentService.deleteComment(commentId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL COMMENTS
     *
     * @param taskId UUID
     * @return COMMENTS LIST
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getAllComments(@PathVariable UUID taskId) {
        List<Comment> comments = commentService.getAllComments(taskId);
        return ResponseEntity.status(comments.size() != 0 ? 200 : 409).body(comments);
    }


    /**
     * EDIT COMMENT
     *
     * @param commentId  UUID
     * @param commentDTO TEXT
     * @return API RESPONSE
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable UUID commentId,
                                         @Valid @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.editComment(commentId, commentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

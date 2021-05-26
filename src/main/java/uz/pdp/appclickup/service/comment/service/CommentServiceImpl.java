package uz.pdp.appclickup.service.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.task.Comment;
import uz.pdp.appclickup.entity.task.Task;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CommentDTO;
import uz.pdp.appclickup.repository.CommentRepository;
import uz.pdp.appclickup.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TaskRepository taskRepository;


    /**
     * ADD COMMENT
     *
     * @param taskId     UUID
     * @param commentDTO TEXT
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addComment(UUID taskId, CommentDTO commentDTO) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();

        commentRepository.save(new Comment(
                commentDTO.getText(),
                task
        ));

        return new ApiResponse("Comment added", true);
    }


    /**
     * DELETE COMMENT
     *
     * @param commentId UUID
     * @return API RESPONSE
     */
    @Override
    public ApiResponse deleteComment(UUID commentId) {
        try {
            commentRepository.deleteById(commentId);
            return new ApiResponse("Comment deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL COMMENTS
     *
     * @param taskId UUID
     * @return COMMENTS LIST
     */
    @Override
    public List<Comment> getAllComments(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ArrayList<>();
        }

        return commentRepository.findAllByTaskId(taskId);
    }


    /**
     * EDIT COMMENT
     *
     * @param commentId  UUID
     * @param commentDTO TEXT
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editComment(UUID commentId, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found", false);
        }
        Comment comment = optionalComment.get();
        comment.setText(commentDTO.getText());
        commentRepository.save(comment);

        return new ApiResponse("Comment edited", true);
    }
}

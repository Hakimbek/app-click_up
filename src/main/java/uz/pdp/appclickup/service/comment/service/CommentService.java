package uz.pdp.appclickup.service.comment.service;

import uz.pdp.appclickup.entity.task.Comment;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    ApiResponse addComment(UUID taskId, CommentDTO commentDTO);

    ApiResponse deleteComment(UUID commentId);

    List<Comment> getAllComments(UUID taskId);

    ApiResponse editComment(UUID commentId, CommentDTO commentDTO);
}

package uz.pdp.appclickup.service.tag.service;

import uz.pdp.appclickup.entity.task.Tag;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDTO;

import java.util.List;
import java.util.UUID;

public interface TagService {
    ApiResponse addTag(Long workspaceId, TagDTO tagDTO);

    ApiResponse deleteTag(UUID tagId);

    List<Tag> getAllTags(Long workspaceId);

    Tag getOneTag(UUID tagId);

    ApiResponse editTag(UUID tagId, TagDTO tagDTO);
}

package uz.pdp.appclickup.service.tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.task.Tag;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDTO;
import uz.pdp.appclickup.repository.TagRepository;
import uz.pdp.appclickup.repository.WorkspaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;


    /**
     * ADD TAG
     *
     * @param workspaceId LONG
     * @param tagDTO      NAME, COLOR
     * @return API RESPONSE
     */
    @Override
    public ApiResponse addTag(Long workspaceId, TagDTO tagDTO) {
        if (tagRepository.existsByNameAndWorkSpaceId(tagDTO.getName(), workspaceId)) {
            return new ApiResponse("Tag already exist", false);
        }

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();

        tagRepository.save(new Tag(
                tagDTO.getName(),
                tagDTO.getColor(),
                workspace
        ));

        return new ApiResponse("Tag created", true);
    }


    /**
     * DELETE TAG
     *
     * @param tagId UUID
     * @return API RESSPONSE
     */
    @Override
    public ApiResponse deleteTag(UUID tagId) {
        try {
            tagRepository.deleteById(tagId);
            return new ApiResponse("Tag deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET ALL TAGS
     *
     * @param workspaceId LONG
     * @return TAGS LIST
     */
    @Override
    public List<Tag> getAllTags(Long workspaceId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);
        if (!optionalWorkspace.isPresent()) {
            return new ArrayList<>();
        }

        return tagRepository.findAllByWorkSpaceId(workspaceId);
    }


    /**
     * GET ONE TAG
     *
     * @param tagId UUID
     * @return TAG
     */
    @Override
    public Tag getOneTag(UUID tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        return optionalTag.orElse(null);
    }


    /**
     * EDIT TAG
     *
     * @param tagId  UUID
     * @param tagDTO NAME, COLOR
     * @return API RESPONSE
     */
    @Override
    public ApiResponse editTag(UUID tagId, TagDTO tagDTO) {
        if (tagRepository.existsByNameAndIdNot(tagDTO.getName(), tagId)) {
            return new ApiResponse("Tag already exist", false);
        }

        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (!optionalTag.isPresent()) {
            return new ApiResponse("Tag not found", false);
        }
        Tag tag = optionalTag.get();
        tag.setName(tagDTO.getName());
        tag.setColor(tagDTO.getColor());
        tagRepository.save(tag);

        return new ApiResponse("Tag edited", true);
    }
}

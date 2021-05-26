package uz.pdp.appclickup.service.space.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.entity.space.SpaceUser;
import uz.pdp.appclickup.entity.workspace.Workspace;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDTO;
import uz.pdp.appclickup.repository.SpaceRepository;
import uz.pdp.appclickup.repository.SpaceUserRepository;
import uz.pdp.appclickup.repository.UserRepository;
import uz.pdp.appclickup.repository.WorkspaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    SpaceUserRepository spaceUserRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * ADD SPACE
     *
     * @param id       LONG
     * @param spaceDTO
     * @param user
     * @return Api Responce
     */
    @Override
    public ApiResponse addSpace(Long id, SpaceDTO spaceDTO, User user) {
        // shunday nomli space yoqligini tekshirdim
        if (spaceRepository.existsByNameAndWorkspaceId(spaceDTO.getName(), id)) {
            return new ApiResponse("Space already exist", false);
        }

        // workspace ni oldim
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();

        // space ni saqladim
        Space savedSpace = spaceRepository.save(new Space(
                spaceDTO.getName(),
                spaceDTO.getColor(),
                workspace,
                user,
                spaceDTO.getAccessType()
        ));

        // agar isPrivate ti true bo'lsa spaceUser ga userlarni qo'shdim
        if (savedSpace.getIsPrivate().equals(true)) {
            List<SpaceUser> spaceUsers = new ArrayList<>();

            for (UUID spaceDTOUser : spaceDTO.getUsers()) {
                Optional<User> optionalUser = userRepository.findById(spaceDTOUser);
                if (optionalUser.isPresent()) {
                    User userForSpace = optionalUser.get();
                    SpaceUser spaceUser = new SpaceUser(savedSpace, userForSpace);
                    spaceUsers.add(spaceUser);
                }
            }

            spaceUserRepository.saveAll(spaceUsers);
        }

        return new ApiResponse("Success", true);
    }


    /**
     * DELETE SPACE
     *
     * @param id UUID
     * @return Api Response
     */
    @Override
    public ApiResponse deleteSpace(UUID id) {
        try {
            spaceRepository.deleteById(id);
            return new ApiResponse("Success", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * GET SPACES LIST
     *
     * @param id LONG
     * @return Api Response
     */
    @Override
    public List<Space> getSpaces(Long id) {
        return spaceRepository.findAllByWorkspaceId(id);
    }


    /**
     * GET ONE SPACE
     *
     * @param id LONG
     * @return Api Response
     */
    @Override
    public Space getOneSpace(UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        return optionalSpace.orElse(null);
    }


    /**
     * EDIT SPACE, ADD OR REMOVE SPACE USER
     * @param id UUID
     * @param spaceDTO
     * @return Api Response
     */
    @Override
    public ApiResponse editSpace(UUID id, SpaceDTO spaceDTO) {
        if (spaceRepository.existsByNameAndIdNot(spaceDTO.getName(), id)) {
            return new ApiResponse("Space already exist", false);
        }

        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        space.setName(spaceDTO.getName());
        space.setColor(spaceDTO.getColor());
        space.setIsPrivate(spaceDTO.getAccessType());
        Space savedSpace = spaceRepository.save(space);

        spaceUserRepository.deleteAllBySpaceId(id);

        if (savedSpace.getIsPrivate().equals(true)) {
            List<SpaceUser> spaceUsers = new ArrayList<>();

            for (UUID spaceDTOUser : spaceDTO.getUsers()) {
                Optional<User> optionalUser = userRepository.findById(spaceDTOUser);
                if (optionalUser.isPresent()) {
                    User userForSpace = optionalUser.get();
                    SpaceUser spaceUser = new SpaceUser(savedSpace, userForSpace);
                    spaceUsers.add(spaceUser);
                }
            }

            spaceUserRepository.saveAll(spaceUsers);
        }

        return new ApiResponse("Success", true);
    }
}

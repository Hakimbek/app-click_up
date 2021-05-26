package uz.pdp.appclickup.service.space.service;

import uz.pdp.appclickup.entity.User;
import uz.pdp.appclickup.entity.space.Space;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.SpaceDTO;

import java.util.List;
import java.util.UUID;

public interface SpaceService {
    ApiResponse addSpace(Long id, SpaceDTO spaceDTO, User user);

    ApiResponse deleteSpace(UUID id);

    List<Space> getSpaces(Long id);

    Space getOneSpace(UUID id);

    ApiResponse editSpace(UUID id, SpaceDTO spaceDTO);
}

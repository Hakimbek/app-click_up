package uz.pdp.appclickup.service.status.service;

import uz.pdp.appclickup.entity.Status;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.StatusDTO;

import java.util.List;
import java.util.UUID;

public interface StatusService {
    ApiResponse addStatus(StatusDTO statusDTO);

    ApiResponse deleteStatus(UUID statusId);

    List<Status> getAllSpaceStatus(UUID spaceId);

    List<Status> getAllProjectStatus(UUID projectId);

    List<Status> getAllCategoryStatus(UUID categoryId);

    ApiResponse editStatus(UUID statusId, StatusDTO statusDTO);

    Status getOneStatus(UUID statusId);
}

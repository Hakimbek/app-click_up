package uz.pdp.appclickup.service.permission.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionDTO;

public interface PermissionService {
    ApiResponse addOrRemovePermission(PermissionDTO permissionDTO);
}

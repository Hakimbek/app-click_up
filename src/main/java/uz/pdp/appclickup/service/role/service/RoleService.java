package uz.pdp.appclickup.service.role.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.RoleDTO;

public interface RoleService {
    ApiResponse addRole(Long id, RoleDTO roleDTO);
}

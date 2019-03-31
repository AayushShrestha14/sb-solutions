package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
public interface RolePermissionRightService extends BaseService<RolePermissionRights> {
    List<RolePermissionRights> getByRoleId(Long id);

    void saveList(List<RolePermissionRights> rolePermissionRightsList);

}

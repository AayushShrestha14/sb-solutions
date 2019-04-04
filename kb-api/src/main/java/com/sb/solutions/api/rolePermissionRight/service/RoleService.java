package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
public interface RoleService extends BaseService<Role> {
    Map<Object, Object> roleStatusCount();

    List<Role> activeRole();


}

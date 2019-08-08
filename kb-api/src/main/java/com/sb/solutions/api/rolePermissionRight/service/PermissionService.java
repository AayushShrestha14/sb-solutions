package com.sb.solutions.api.rolePermissionRight.service;

import java.util.List;
import java.util.Map;

import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
public interface PermissionService extends BaseService<Permission> {

    List<Map<String, Object>> permsRight(Long role);
}

package com.sb.solutions.api.rolePermissionRight.service;

import java.util.List;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 5/13/2019
 */
public interface RoleHierarchyService extends BaseService<RoleHierarchy> {

    List<RoleHierarchy> SaveList(List<RoleHierarchy> roleHierarchyList);

    List<RoleHierarchy> roleHierarchyByCurrentRole(Long id);

    List<RoleHierarchy> roleHierarchyByCurrentRoleBackward(Long id);
}

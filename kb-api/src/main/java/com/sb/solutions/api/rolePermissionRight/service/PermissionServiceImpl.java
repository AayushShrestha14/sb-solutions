package com.sb.solutions.api.rolePermissionRight.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import com.sb.solutions.api.rolePermissionRight.repository.PermissionRepository;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.getAllForRoleAndPermission();
    }

    @Override
    public Permission findOne(Long id) {
        return permissionRepository.findById(id).get();
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Page<Permission> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<Map<String, Object>> permsRight(Long role) {
        return permissionRepository.permsRight(role);
    }
}

package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.Permission;
import com.sb.solutions.api.rolePermissionRight.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

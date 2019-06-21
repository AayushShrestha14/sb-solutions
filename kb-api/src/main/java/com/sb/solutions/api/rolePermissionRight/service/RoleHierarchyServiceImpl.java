package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.api.rolePermissionRight.repository.RoleHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 5/13/2019
 */
@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    @Autowired
    RoleHierarchyRepository roleHierarchyRepository;

    @Override
    public List<RoleHierarchy> findAll() {
        return roleHierarchyRepository.findAll();
    }

    @Override
    public RoleHierarchy findOne(Long id) {
        return roleHierarchyRepository.findById(id).get();
    }

    @Override
    public RoleHierarchy save(RoleHierarchy roleHierarchy) {
        return roleHierarchyRepository.save(roleHierarchy);
    }

    @Override
    public Page<RoleHierarchy> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<RoleHierarchy> SaveList(List<RoleHierarchy> roleHierarchyList) {
        return roleHierarchyRepository.saveAll(roleHierarchyList);

    }

    @Override
    public List<RoleHierarchy> roleHierarchyByCurrentRoleForward(Long id) {
        RoleHierarchy r = roleHierarchyRepository.findByRole(id);
        return roleHierarchyRepository.roleHierarchyByCurrentRoleForward(r.getRoleOrder());
    }

    @Override
    public List<RoleHierarchy> roleHierarchyByCurrentRoleBackward(Long id) {
        RoleHierarchy r = roleHierarchyRepository.findByRole(id);
        return roleHierarchyRepository.roleHierarchyByCurrentRoleBackward(r.getRoleOrder());
    }
}
package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findOne(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Role save(Role role) {
        role.setLastModified(new Date());
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}

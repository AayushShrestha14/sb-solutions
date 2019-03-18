package com.sb.solutions.api.roleAndPermission.service;

import com.sb.solutions.api.roleAndPermission.entity.Permission;
import com.sb.solutions.api.roleAndPermission.entity.Rights;
import com.sb.solutions.api.roleAndPermission.entity.Role;
import com.sb.solutions.api.roleAndPermission.repository.RightsRepository;
import com.sb.solutions.api.roleAndPermission.repository.RoleAndPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

@Service
public class RoleAndPermissionServiceImpl implements  RoleAndPermissionService{

    @Autowired
    RoleAndPermissionRepository roleAndPermissionRepository;

    @Autowired
    RightsRepository rightsRepository;

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleAndPermissionRepository.findAll();

        for(Role r :roles){
            for(Permission p :r.getPermission()){
                List<Rights> rightses = new ArrayList<>();
                rightses = rightsRepository.getRightByPermissionAndRole(r.getId(),p.getId());
                p.setRightsList(rightses);
            }

        }
        return roles;
    }

    @Override
    public Role findOne(Long id) {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Page<Role> findAllPageable(Role role, Pageable pageable) {
        return null;
    }
}

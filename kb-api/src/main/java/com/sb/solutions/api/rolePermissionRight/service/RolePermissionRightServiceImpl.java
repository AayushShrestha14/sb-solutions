package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.api.rolePermissionRight.repository.RolePermissionRightRepository;
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
public class RolePermissionRightServiceImpl implements RolePermissionRightService {
    @Autowired
    RolePermissionRightRepository rolePermissionRightRepository;

    @Override
    public List<RolePermissionRights> findAll() {
        return rolePermissionRightRepository.findAll();
    }

    @Override
    public RolePermissionRights findOne(Long id) {
        return null;
    }

    @Override
    public RolePermissionRights save(RolePermissionRights rolePermissionRights) {
        rolePermissionRights.setLastModified(new Date());
        return rolePermissionRightRepository.save(rolePermissionRights);
    }

    @Override
    public Page<RolePermissionRights> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<RolePermissionRights> getByRoleId(Long id) {
        return rolePermissionRightRepository.findByRole(id);
    }

    @Override
    public void saveList(List<RolePermissionRights> rolePermissionRightsList) {
        Long id = null;
        for (RolePermissionRights r : rolePermissionRightsList) {
            id = r.getRole() == null ? null : r.getRole().getId();

        }
        System.out.println(id);
       // rolePermissionRightRepository.deleteRolePermissionRightsByRole(id);
        rolePermissionRightRepository.saveAll(rolePermissionRightsList);
    }
}

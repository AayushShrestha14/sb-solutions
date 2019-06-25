package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.repository.RoleRepository;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 3/28/2019
 */

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserService userService;

    public RoleServiceImpl(@Autowired RoleRepository roleRepository,
                           @Autowired UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

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
        role.setRoleName(role.getRoleName().toUpperCase());
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public Map<Object, Object> roleStatusCount() {
        return roleRepository.roleStatusCount();
    }

    @Override
    public List<Map<Object, Object>> activeRole() {
        User u = userService.getAuthenticated();
        Role r = u.getRole();
        return roleRepository.activeRole(r.getId());
    }

    @Override
    public boolean isMaker() {
        return roleRepository.chkByRoleType(RoleType.MAKER);
    }

    @Override
    public List<Role> getApproval() {
        return roleRepository.getByRoleTypeAndStatus(RoleType.APPROVAL, Status.ACTIVE);
    }
}

package com.sb.solutions.api.user.service;

import com.sb.solutions.api.user.entity.Role;
import com.sb.solutions.api.user.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findOne(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> findAllPageable(Object userType, Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}

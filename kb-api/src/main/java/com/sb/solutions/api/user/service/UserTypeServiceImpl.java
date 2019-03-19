package com.sb.solutions.api.user.service;

import com.sb.solutions.api.user.entity.UserType;
import com.sb.solutions.api.user.repository.UserTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }

    @Override
    public UserType findOne(Long id) {
        return userTypeRepository.getOne(id);
    }

    @Override
    public UserType save(UserType userType) {
        return userTypeRepository.save(userType);
    }

    @Override
    public Page<UserType> findAllPageable(Object userType, Pageable pageable) {
        return userTypeRepository.findAll(pageable);
    }
}

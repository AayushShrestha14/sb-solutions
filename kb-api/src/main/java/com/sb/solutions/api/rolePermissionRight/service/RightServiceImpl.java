package com.sb.solutions.api.rolePermissionRight.service;

import com.sb.solutions.api.rolePermissionRight.entity.Rights;
import com.sb.solutions.api.rolePermissionRight.repository.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 3/31/2019
 */
@Service
public class RightServiceImpl implements RightService {
    @Autowired
    RightRepository rightRepository;

    @Override
    public List<Rights> getAll() {
        return rightRepository.findAll();
    }
}

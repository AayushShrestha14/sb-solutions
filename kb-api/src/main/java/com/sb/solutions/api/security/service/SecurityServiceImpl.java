package com.sb.solutions.api.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.repository.SecurityRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

    final SecurityRepository securityRepository;

    @Autowired
    public SecurityServiceImpl(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @Override
    public Security findOne(Long id) {
        return securityRepository.getOne(id);
    }

    @Override
    public Security save(Security security) {
        return securityRepository.save(security);
    }

    @Override
    public Page<Security> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<Security> saveAll(List<Security> securityList) {
        return securityRepository.saveAll(securityList);
    }
}

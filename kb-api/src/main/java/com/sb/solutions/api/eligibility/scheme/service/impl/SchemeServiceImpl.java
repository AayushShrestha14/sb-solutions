package com.sb.solutions.api.eligibility.scheme.service.impl;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.api.eligibility.scheme.repository.SchemeRepository;
import com.sb.solutions.api.eligibility.scheme.service.SchemeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SchemeServiceImpl implements SchemeService {

    private final SchemeRepository schemeRepository;

    @Override
    public List<Scheme> findAll() {
        return schemeRepository.findAll();
    }

    @Override
    public Scheme findOne(Long id) {
        return schemeRepository.getOne(id);
    }

    @Override
    public Scheme save(Scheme scheme) {
        scheme.setLastModifiedAt(new Date());
        return schemeRepository.save(scheme);
    }

    @Override
    public Page<Scheme> findAllPageable(Scheme scheme, Pageable pageable) {
        return schemeRepository.findAll(pageable);
    }

    @Override
    public Page<Scheme> findAllPageable(Long companyId, Pageable pageable) {
        return schemeRepository.findAllByCompanyId(companyId, pageable);
    }
}

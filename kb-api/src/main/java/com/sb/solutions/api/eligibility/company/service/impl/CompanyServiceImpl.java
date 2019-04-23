package com.sb.solutions.api.eligibility.company.service.impl;

import com.sb.solutions.api.eligibility.company.entity.Company;
import com.sb.solutions.api.eligibility.company.repository.CompanyRepository;
import com.sb.solutions.api.eligibility.company.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findOne(Long id) {
        return companyRepository.getOne(id);
    }

    @Override
    public Company save(Company company) {
        company.setLastModifiedAt(new Date());
        return companyRepository.save(company);
    }

    @Override
    public Page<Company> findAllPageable(Object company, Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}

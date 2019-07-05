package com.sb.solutions.api.company.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.company.entity.Company;
import com.sb.solutions.api.company.repository.CompanyRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;

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
        if (company.getId() == null) {
            company.setStatus(Status.ACTIVE);
        }
        return companyRepository.save(company);
    }

    @Override
    public Page<Company> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object, SearchDto.class);
        return companyRepository.companyFilter(s.getName() == null ? "" : s.getName(), pageable);
    }

    @Override
    public Map<Object, Object> companyStatusCount() {
        return companyRepository.companyStatusCount();
    }
}

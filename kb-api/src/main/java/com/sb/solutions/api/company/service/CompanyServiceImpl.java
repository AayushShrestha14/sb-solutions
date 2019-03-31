package com.sb.solutions.api.company.service;

import com.sb.solutions.api.company.entity.Company;
import com.sb.solutions.api.company.repository.CompanyRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        company.setLastModified(new Date());
        if(company.getId()==null){
            company.setStatus(Status.ACTIVE);
        }
        return companyRepository.save(company);
    }

    @Override
    public Page<Company> findAllPageable(Object company, Pageable pageable) {
        Company companyMapped = (Company) company;
        return companyRepository.companyFilter(companyMapped.getCompanyName()==null?"":companyMapped.getCompanyName(),pageable);
    }

    @Override
    public Map<Object, Object> companyStatusCount() {
        return companyRepository.companyStatusCount();
    }
}

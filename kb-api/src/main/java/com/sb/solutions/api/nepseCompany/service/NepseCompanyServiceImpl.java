package com.sb.solutions.api.nepseCompany.service;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.repository.NepseCompanyRepository;
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
public class NepseCompanyServiceImpl implements  NepseCompanyService{
    private final NepseCompanyRepository nepseCompanyRepository;
    @Override
    public List<NepseCompany> findAll() {
        return nepseCompanyRepository.findAll();
    }

    @Override
    public NepseCompany findOne(Long id) {
        return nepseCompanyRepository.getOne(id);
    }

    @Override
    public NepseCompany save(NepseCompany nepseCompany) {
        nepseCompany.setLastModified(new Date());
        if(nepseCompany.getId()==null){
            nepseCompany.setStatus(Status.ACTIVE);
        }
        return nepseCompanyRepository.save(nepseCompany);
    }

    @Override
    public Page<NepseCompany> findAllPageable(Object nepseCompany, Pageable pageable) {
        NepseCompany nepseCompanyMapped = (NepseCompany) nepseCompany;
        return nepseCompanyRepository.nepseCompanyFilter(nepseCompanyMapped.getCompanyName()==null?"":nepseCompanyMapped.getCompanyName(),pageable);
    }

    @Override
    public Map<Object, Object> nepseStatusCount() {
        return nepseCompanyRepository.nepseCompanyStatusCount();
    }

    @Override
    public void saveList(List<NepseCompany> nepseCompanyList) {
        inactivePreviousCompanies();
        for(NepseCompany nepseCompany : nepseCompanyList){
            nepseCompany.setStatus(Status.ACTIVE);
            nepseCompanyRepository.save(nepseCompany);
        }

    }

    private void inactivePreviousCompanies() {
        List<NepseCompany> nepseCompanies = nepseCompanyRepository.findByStatus(Status.ACTIVE);
        for (NepseCompany nepseCompany : nepseCompanies) {
            nepseCompany.setStatus(Status.INACTIVE);
            nepseCompanyRepository.save(nepseCompany);
        }
    }
}

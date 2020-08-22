package com.sb.solutions.api.companyInfo.model.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.companyInfo.model.repository.CompanyInfoRepository;
import com.sb.solutions.api.companyInfo.model.repository.specification.CompanyInfoSpecBuilder;
import com.sb.solutions.api.customer.service.CustomerInfoService;
import com.sb.solutions.core.exception.ServiceValidationException;


@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;
    private final CustomerInfoService customerInfoService;

    public CompanyInfoServiceImpl(
            @Autowired CompanyInfoRepository companyInfoRepository,
            CustomerInfoService customerInfoService) {
        this.companyInfoRepository = companyInfoRepository;
        this.customerInfoService = customerInfoService;
    }

    @Override
    public List<CompanyInfo> findAll() {
        return companyInfoRepository.findAll();
    }

    @Override
    public CompanyInfo findOne(Long id) {
        return companyInfoRepository.getOne(id);
    }

    @Transactional
    @Override
    public CompanyInfo save(CompanyInfo companyInfo) {
        if (!companyInfo.isValid()) {
            throw new ServiceValidationException(
                companyInfo.getValidationMsg());
        }
        final CompanyInfo info = companyInfoRepository.save(companyInfo);
        customerInfoService.saveObject(info);
        return info;
    }

    @Override
    public Page<CompanyInfo> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        final CompanyInfoSpecBuilder companyInfoSpecBuilder = new CompanyInfoSpecBuilder(s);
        Specification<CompanyInfo> specification = companyInfoSpecBuilder.build();
        return companyInfoRepository.findAll(specification, pageable);
    }

    @Override
    public List<CompanyInfo> saveAll(List<CompanyInfo> list) {
        return companyInfoRepository.saveAll(list);
    }

    @Override
    public CompanyInfo findCompanyInfoByRegistrationNumber(String registrationNumber) {
        return companyInfoRepository.findCompanyInfoByRegistrationNumber(registrationNumber);
    }
}

package com.sb.solutions.api.insurance.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sb.solutions.api.insurance.entity.Insurance;
import com.sb.solutions.api.insurance.repository.InsuranceRepository;
import com.sb.solutions.api.insurance.repository.spec.InsuranceSpecBuilder;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;

@Service
public class InsuranceServiceImpl extends BaseServiceImpl<Insurance, Long> implements
    InsuranceService {

    private final InsuranceRepository repository;

    protected InsuranceServiceImpl(
        InsuranceRepository repository) {
        super(repository);

        this.repository = repository;
    }


    @Override
    protected BaseSpecBuilder<Insurance> getSpec(Map<String, String> filterParams) {
        return new InsuranceSpecBuilder(filterParams);
    }
}

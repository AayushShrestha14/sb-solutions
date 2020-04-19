package com.sb.solutions.api.insurance.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sb.solutions.api.insurance.entity.InsuranceHistory;
import com.sb.solutions.api.insurance.repository.InsuranceHistoryRepository;
import com.sb.solutions.api.insurance.repository.spec.InsuranceHistorySpecBuilder;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;

/**
 * @author Elvin Shrestha on 4/19/2020
 */
@Service
public class InsuranceHistoryServiceImpl extends BaseServiceImpl<InsuranceHistory, Long> implements
    InsuranceHistoryService {

    private final InsuranceHistoryRepository repository;

    public InsuranceHistoryServiceImpl(
        InsuranceHistoryRepository repository) {
        super(repository);

        this.repository = repository;
    }

    @Override
    protected BaseSpecBuilder<InsuranceHistory> getSpec(Map<String, String> filterParams) {
        return new InsuranceHistorySpecBuilder(filterParams);
    }
}

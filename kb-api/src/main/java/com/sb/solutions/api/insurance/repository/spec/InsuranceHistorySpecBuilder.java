package com.sb.solutions.api.insurance.repository.spec;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.insurance.entity.InsuranceHistory;
import com.sb.solutions.core.repository.BaseSpecBuilder;

/**
 * @author Elvin Shrestha on 4/19/2020
 */
public class InsuranceHistorySpecBuilder extends BaseSpecBuilder<InsuranceHistory> {

    public InsuranceHistorySpecBuilder(Map<String, String> params) {
        super(params);
    }

    @Override
    protected Specification<InsuranceHistory> getSpecification(String property,
        String filterValue) {
        return new InsuranceHistorySpec(property, filterValue);
    }
}

package com.sb.solutions.repository.specification;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.entity.CustomerApproveLoanCadDocumentation;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/
public class CustomerCadSpecBuilder extends BaseSpecBuilder<CustomerApproveLoanCadDocumentation> {


    public CustomerCadSpecBuilder(Map<String, String> params) {
        super(params);
    }

    @Override
    protected Specification<CustomerApproveLoanCadDocumentation> getSpecification(String property,
        String filterValue) {
        return new CustomerCadSpec(property, filterValue);
    }
}

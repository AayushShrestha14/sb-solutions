package com.sb.solutions.api.customer.repository.specification;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.customer.entity.CustomerActivityLog;
import com.sb.solutions.core.repository.BaseSpecBuilder;

/**
 * @author Elvin Shrestha on 9/19/2020
 */
public class CustomerActivityLogSpecBuilder extends BaseSpecBuilder<CustomerActivityLog> {

    public CustomerActivityLogSpecBuilder(Map<String, String> params) {
        super(params);
    }

    @Override
    protected Specification<CustomerActivityLog> getSpecification(String property,
        String filterValue) {
        return new CustomerActivityLogSpec(property, filterValue);
    }
}

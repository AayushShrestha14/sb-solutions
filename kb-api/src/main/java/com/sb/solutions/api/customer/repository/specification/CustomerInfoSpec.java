package com.sb.solutions.api.customer.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.customer.entity.CustomerInfo;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public class CustomerInfoSpec implements Specification<CustomerInfo> {

    private static final String FILTER_BY_CUSTOMER_NAME = "name";

    private final String property;
    private final String value;

    public CustomerInfoSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<CustomerInfo> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {

        switch (property) {

            case FILTER_BY_CUSTOMER_NAME:
                return criteriaBuilder
                    .like(criteriaBuilder
                            .lower(root.get(property)),
                        value.toLowerCase() + "%");
            default:
                return null;
        }
    }
}



package com.sb.solutions.api.customer.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public class CustomerInfoSpec implements Specification<CustomerInfo> {

    private static final String FILTER_BY_CUSTOMER_NAME = "name";
    private static final String FILTER_BY_CUSTOMER_TYPE = "customerType";
    private static final String FILTER_BY_CUSTOMER_ID_TYPE = "idType";

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

            case FILTER_BY_CUSTOMER_TYPE:
                return criteriaBuilder.equal(root.get(property), CustomerType.valueOf(value));

            case FILTER_BY_CUSTOMER_ID_TYPE:
                return criteriaBuilder.equal(root.get(property), CustomerIdType.valueOf(value));
            default:
                return null;
        }
    }
}




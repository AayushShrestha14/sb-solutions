package com.sb.solutions.api.customer.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.customer.entity.CustomerActivityLog;

/**
 * @author Elvin Shrestha on 9/19/2020
 */
public class CustomerActivityLogSpec implements Specification<CustomerActivityLog> {

    private final String property;
    private final String value;

    public CustomerActivityLogSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<CustomerActivityLog> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {
        return null;
    }
}

package com.sb.solutions.api.accountPurpose.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;

public class AccountPurposeSpec implements Specification<AccountPurpose> {

    private static final String FILTER_BY_NAME = "name";
    private final String property;
    private final String value;

    public AccountPurposeSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<AccountPurpose> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {
        switch (property) {
            case FILTER_BY_NAME:
                return criteriaBuilder.like(root.get(property), value + "%");
            default:
                return null;
        }
    }
}

package com.sb.solutions.api.reportinginfo.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.reportinginfo.entity.ReportingInfo;

/**
 * @author Elvin Shrestha on 3/27/2020
 */
public class ReportingInfoSpec implements Specification<ReportingInfo> {

    private final String property;
    private final String value;

    public ReportingInfoSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<ReportingInfo> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {
        return null;
    }
}

package com.sb.solutions.api.eligibility.applicant.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.api.eligibility.common.SearchCriteria;

public class ApplicantSpecification implements Specification<Applicant> {

    private static final String FILTER_BY_ELIGIBILITY_STATUS = "eligibilityStatus";
    private static final String FILTER_BY_LOAN = "loanConfigId";
    private static final String FILTER_BY_BRANCH = "branchId";

    private final SearchCriteria searchCriteria;


    public ApplicantSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Applicant> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getKey()) {
            case FILTER_BY_ELIGIBILITY_STATUS:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()),
                    EligibilityStatus.valueOf(String.valueOf(searchCriteria.getValue())));
            case FILTER_BY_LOAN:
                return criteriaBuilder.equal(root.join("loanConfig").get("id"),
                    Long.valueOf(String.valueOf(searchCriteria.getValue())));
            case FILTER_BY_BRANCH:
                return criteriaBuilder.equal(root.join("branch").get("id"),
                    Long.valueOf(String.valueOf(searchCriteria.getValue())));
            default:
                return null;
        }
    }
}

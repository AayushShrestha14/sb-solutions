package com.sb.solutions.api.Loan.repository.specification;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Rujan Maharjan on 6/8/2019
 */
public class CustomerLoanSpec implements Specification<CustomerLoan> {

    private static final String FILTER_BY_LOAN = "loanConfigId";
    private static final String FILTER_BY_DOC_STATUS = "documentStatus";

    private final String property;
    private final String value;

    public CustomerLoanSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }


    @Override
    public Predicate toPredicate(Root<CustomerLoan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (property) {

            case FILTER_BY_DOC_STATUS:
                return criteriaBuilder.equal(root.get(property), DocStatus.valueOf(value));

            case FILTER_BY_LOAN:
                return criteriaBuilder.and(criteriaBuilder.equal(root.join("loan").get("id"), Long.valueOf(value)));
            default:
                return null;
        }
    }

}

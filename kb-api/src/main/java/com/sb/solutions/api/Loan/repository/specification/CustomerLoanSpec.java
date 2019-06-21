package com.sb.solutions.api.Loan.repository.specification;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * @author Rujan Maharjan on 6/8/2019
 */
public class CustomerLoanSpec implements Specification<CustomerLoan> {

    private static final String FILTER_BY_LOAN = "loanConfigId";
    private static final String FILTER_BY_DOC_STATUS = "documentStatus";
    private static final String FILTER_BY_CURRENT_USER_ROLE = "currentUserRole";
    private static final String FILTER_BY_ROLE_MAKER = "createdBy";
    private static final String FILTER_BY_BRANCH = "branchId";

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

            case FILTER_BY_CURRENT_USER_ROLE:
                return criteriaBuilder.equal(root.join("currentStage", JoinType.LEFT).join("toRole").get("id"), Long.valueOf(value));

            case FILTER_BY_BRANCH:
                return criteriaBuilder.equal(root.join("branch").get("id"), Long.valueOf(value));

            case FILTER_BY_ROLE_MAKER:
                return null;

            default:
                return null;
        }
    }

}

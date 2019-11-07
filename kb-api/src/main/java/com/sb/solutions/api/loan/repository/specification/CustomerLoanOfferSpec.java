package com.sb.solutions.api.loan.repository.specification;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;

public class CustomerLoanOfferSpec implements Specification<CustomerOfferLetter> {

    private static final String FILTER_BY_LOAN = "loanConfigId";
    private static final String FILTER_BY_DOC_STATUS = "documentStatus";
    private static final String FILTER_BY_CURRENT_USER_ROLE = "currentUserRole";
    private static final String FILTER_BY_TO_USER = "toUser";
    private static final String FILTER_BY_BRANCH = "branchIds";
    private static final String FILTER_BY_CURRENT_STAGE_DATE = "currentStageDate";
    private static final String FILTER_BY_TYPE = "loanNewRenew";
    private static final String FILTER_BY_NOTIFY = "notify";
    private static final String FILTER_BY_CUSTOMER_NAME = "customerName";
    private static final String FILTER_BY_CURRENT_OFFER_LETTER_STAGE = "currentOfferLetterStage";

    private final String property;
    private final String value;

    public CustomerLoanOfferSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }


    @Override
    public Predicate toPredicate(Root<CustomerOfferLetter> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) {

        switch (property) {
            case FILTER_BY_LOAN:
                return criteriaBuilder
                    .equal(root.join("customerLoan", JoinType.LEFT)
                            .join("loan", JoinType.LEFT).get("id"),
                        Long.valueOf(value));


            case FILTER_BY_BRANCH:
                Pattern pattern = Pattern.compile(",");
                List<Long> list = pattern.splitAsStream(value)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
                Expression<String> exp = root.join("customerLoan").join("branch").get("id");
                Predicate predicate = exp.in(list);
                return criteriaBuilder.and(predicate);

        }
        return null;
    }
}

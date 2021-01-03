package com.sb.solutions.repository.specification;

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

import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.enums.CADDocumentType;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/
public class CustomerCadSpec implements Specification<CustomerApprovedLoanCadDocumentation> {



    private static final String FILTER_BY_BRANCH = "branchIds";

    private static final String FILTER_BY_DOC_STATUS = "docStatus";
    private static final String FILTER_BY_TO_USER = "toUser";

    private final String property;
    private final String value;

    CustomerCadSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<CustomerApprovedLoanCadDocumentation> root,
        CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (property) {


            case FILTER_BY_BRANCH:
                Pattern pattern = Pattern.compile(",");
                List<Long> list = pattern.splitAsStream(value)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
                Expression<String> exp = root.join("loanHolder").get("branch").get("id");
                Predicate predicate = exp.in(list);
                return criteriaBuilder.and(predicate);

            case FILTER_BY_TO_USER:
                return criteriaBuilder
                    .equal(
                        root.join("cadCurrentStage", JoinType.LEFT).join(FILTER_BY_TO_USER).get("id"),
                        Long.valueOf(value));

            case FILTER_BY_DOC_STATUS:
                return criteriaBuilder.equal(root.get(property), DocStatus.valueOf(value));

            default:
                return null;
        }
    }

}

package com.sb.solutions.repository.specification;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.entity.CustomerApproveLoanCadDocumentation;
import com.sb.solutions.enums.CADDocumentType;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/
public class CustomerCadSpec implements Specification<CustomerApproveLoanCadDocumentation> {

    private static final String FILTER_BY_CAD_DOCUMENT_TYPE = "cadDocumentType";

    private static final String FILTER_BY_CHILD_ID = "childId";

    private static final String FILTER_BY_PARENT_ID = "parentId";

    private static final String FILTER_BY_BRANCH = "branchIds";
    private static final String FILTER_BY_DOC_STATUS = "docStatus";

    private final String property;
    private final String value;

    CustomerCadSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<CustomerApproveLoanCadDocumentation> root,
        CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (property) {
            case FILTER_BY_CAD_DOCUMENT_TYPE:
                return criteriaBuilder.equal(root.get(property), CADDocumentType.valueOf(value));

            case FILTER_BY_CHILD_ID:
                return criteriaBuilder.equal(root.get(property), value);

            case FILTER_BY_BRANCH:
                Pattern pattern = Pattern.compile(",");
                List<Long> list = pattern.splitAsStream(value)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
                Expression<String> exp = root.join("loanHolder").get("branch").get("id");
                Predicate predicate = exp.in(list);
                return criteriaBuilder.and(predicate);

            case FILTER_BY_PARENT_ID:
                return criteriaBuilder.equal(root.get(property), value);

            case FILTER_BY_DOC_STATUS:
                return criteriaBuilder.equal(root.get(property), DocStatus.valueOf(value));

            default:
                return null;
        }
    }

}

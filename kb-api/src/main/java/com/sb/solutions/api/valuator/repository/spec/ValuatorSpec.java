package com.sb.solutions.api.valuator.repository.spec;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.core.enums.RoleAccess;
import com.sb.solutions.core.enums.Status;

/**
 * @author Elvin Shrestha on 1/2/20
 */
public class ValuatorSpec implements Specification<Valuator> {

    private static final String FILTER_BY_NAME = "name";
    private static final String FILTER_BY_BRANCH = "branchIds";
    private static final String FILTER_BY_STATUS = "status";
    private static final String FILTER_BY_VALUATING_FIELD = "valuatingField";

    private final String property;
    private final String value;

    public ValuatorSpec(String property, String value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Valuator> root, CriteriaQuery<?> query,
        CriteriaBuilder criteriaBuilder) {
        switch (property) {
            case FILTER_BY_NAME:
                return criteriaBuilder
                    .like(criteriaBuilder.lower(root.get(property)),
                        "%" + value.toLowerCase() + "%");
            case FILTER_BY_BRANCH:
                Pattern pattern = Pattern.compile(",");
                List<Long> list = pattern.splitAsStream(value)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
                Expression<String> exp = root.join("branch").get("id");
                Predicate predicate = exp.in(list);
                return criteriaBuilder.and(predicate);
            case FILTER_BY_STATUS:
                return criteriaBuilder.equal(root.get(FILTER_BY_STATUS), Status.valueOf(value));
            case FILTER_BY_VALUATING_FIELD:
                return criteriaBuilder.equal(root.get(FILTER_BY_VALUATING_FIELD), value);
            default:
                return null;
        }
    }
}

package com.sb.solutions.api.memo.service.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.memo.entity.MemoType;

public class MemoTypeCriteriaBuilder {

    private final Map<String, String> params;


    public MemoTypeCriteriaBuilder(Map<String, String> params) {
        this.params = params;
    }

    public Specification<MemoType> build() {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        final List<String> properties = new ArrayList<>(params.keySet());

        final String firstProperty = properties.get(0);

        Specification<MemoType> criteria = new MemoTypeCriteria(properties.get(0),
            params.get(firstProperty));

        for (int i = 1; i < properties.size(); i++) {
            final String property = properties.get(i);
            criteria = Specification.where(criteria)
                .and(new MemoTypeCriteria(property, params.get(property)));
        }

        return criteria;
    }
}

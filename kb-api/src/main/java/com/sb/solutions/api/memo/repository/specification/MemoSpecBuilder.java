package com.sb.solutions.api.memo.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.memo.entity.Memo;

public class MemoSpecBuilder {

    private final Map<String, String> params;


    public MemoSpecBuilder(Map<String, String> params) {
        this.params = params;
    }

    public Specification<Memo> build() {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        final List<String> properties = new ArrayList<>(params.keySet());

        final String firstProperty = properties.get(0);

        Specification<Memo> spec = new MemoSpec(properties.get(0),
            params.get(firstProperty));

        for (int i = 1; i < properties.size(); i++) {
            final String property = properties.get(i);
            spec = Specification.where(spec)
                .and(new MemoSpec(property, params.get(property)));
        }

        return spec;
    }
}

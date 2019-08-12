package com.sb.solutions.api.companyInfo.entityInfo.repository.specification;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityInfoSpecBuilder {

    private final Map<String, String> params;

    public EntityInfoSpecBuilder(Map<String, String> params) {
        this.params = params;
    }

    public Specification<EntityInfo> build() {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        final List<String> properties = new ArrayList<>(params.keySet());

        final String firstProperty = properties.get(0);

        Specification<EntityInfo> spec = new EntityInfoSpec(properties.get(0),
                params.get(firstProperty));

        for (int i = 1; i < properties.size(); i++) {
            final String property = properties.get(i);
            spec = Specification.where(spec)
                    .and(new EntityInfoSpec(property, params.get(property)));
        }

        return spec;
    }
}

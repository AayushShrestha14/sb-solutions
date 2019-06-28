package com.sb.solutions.api.eligibility.applicant.repository.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.api.eligibility.common.SearchCriteria;

public class ApplicantSpecificationBuilder {

    private final List<SearchCriteria> queryParams;


    public ApplicantSpecificationBuilder() {
        this.queryParams = new ArrayList<>();
    }

    public ApplicantSpecificationBuilder with(String key, Object value, String operation) {
        queryParams.add(new SearchCriteria(key, value, operation));
        return this;
    }

    public Specification<Applicant> build() {
        if (queryParams.size() == 0) {
            return null;
        }
        List<Specification> specifications =
            queryParams.stream().map(ApplicantSpecification::new).collect(Collectors.toList());
        Specification result = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }
        return result;
    }
}

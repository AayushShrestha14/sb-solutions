package com.sb.solutions.api.eligibility.scheme.service;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import com.sb.solutions.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchemeService extends BaseService<Scheme> {

    Page<Scheme> findAllPageable(Long companyId, Pageable pageable);

}

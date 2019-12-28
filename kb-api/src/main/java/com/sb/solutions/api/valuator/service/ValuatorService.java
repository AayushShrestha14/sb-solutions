package com.sb.solutions.api.valuator.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.core.service.BaseService;

public interface ValuatorService extends BaseService<Valuator> {

    Map<Object, Object> valuatorStatusCount();

    Collection<Valuator> findByBranchIn(Collection<Branch> branche);

}

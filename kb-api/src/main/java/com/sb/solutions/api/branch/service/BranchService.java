package com.sb.solutions.api.branch.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
public interface BranchService extends BaseService<Branch> {

    Map<Object, Object> branchStatusCount();

    String csv(SearchDto searchDto);
}

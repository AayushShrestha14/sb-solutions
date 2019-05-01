package com.sb.solutions.api.loanConfig.service;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

/**
 * @author Rujan Maharjan on 2/26/2019
 */
public interface LoanConfigService extends BaseService<LoanConfig> {

    Map<Object, Object> loanStatusCount();
}

package com.sb.solutions.api.crg.service;

import com.sb.solutions.api.crg.entity.CrgGroup;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
public interface CrgGroupService extends BaseService<CrgGroup> {

    CrgGroup findByStatus(Status status);

}

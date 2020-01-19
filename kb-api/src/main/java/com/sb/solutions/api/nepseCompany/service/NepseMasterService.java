package com.sb.solutions.api.nepseCompany.service;

import com.sb.solutions.api.nepseCompany.entity.NepseMaster;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Sunil Babu Shrestha on 1/17/2020
 */
public interface NepseMasterService extends BaseService<NepseMaster> {

    NepseMaster findActiveMasterRecord();
}

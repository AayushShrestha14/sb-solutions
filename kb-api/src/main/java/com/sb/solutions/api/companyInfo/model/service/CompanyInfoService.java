package com.sb.solutions.api.companyInfo.model.service;

import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.core.service.BaseService;

public interface CompanyInfoService extends BaseService<CompanyInfo> {

    CompanyInfo findCompanyInfoByRegistrationNumber(String registrationNumber);

}

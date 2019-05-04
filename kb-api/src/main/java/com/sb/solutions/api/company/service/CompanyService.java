package com.sb.solutions.api.company.service;

import com.sb.solutions.api.company.entity.Company;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface CompanyService extends BaseService<Company> {
    Map<Object, Object> companyStatusCount();
}

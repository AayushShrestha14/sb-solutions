package com.sb.solutions.api.nepseCompany.service;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.service.BaseService;

import java.util.List;
import java.util.Map;

public interface NepseCompanyService extends BaseService<NepseCompany> {
    Map<Object,Object> nepseStatusCount();
    void saveList(List<NepseCompany> nepseCompanyList);

}

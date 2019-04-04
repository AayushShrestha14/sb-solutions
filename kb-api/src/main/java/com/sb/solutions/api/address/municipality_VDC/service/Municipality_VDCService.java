package com.sb.solutions.api.address.municipality_VDC.service;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipality_VDC.entity.Municipality_VDC;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface Municipality_VDCService extends BaseService<Municipality_VDC> {
    List<Municipality_VDC> findAllByDistrict(District district);
}

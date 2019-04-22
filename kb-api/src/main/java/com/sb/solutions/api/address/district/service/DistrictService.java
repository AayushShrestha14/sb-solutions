package com.sb.solutions.api.address.district.service;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface DistrictService extends BaseService<District> {
    List<District> findAllByProvince(Province province);
}

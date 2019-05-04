package com.sb.solutions.api.valuator.service;

import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface ValuatorService extends BaseService<Valuator> {
    Map<Object, Object> valuatorStatusCount();
}

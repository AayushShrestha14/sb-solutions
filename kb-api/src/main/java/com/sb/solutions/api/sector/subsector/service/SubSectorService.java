package com.sb.solutions.api.sector.subsector.service;

import com.sb.solutions.api.sector.subsector.entity.SubSector;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface SubSectorService extends BaseService<SubSector> {
    Map<Object,Object> subSectorStatusCount();
}

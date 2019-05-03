package com.sb.solutions.api.sector.sector.service;

import com.sb.solutions.api.sector.sector.entity.Sector;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface SectorService extends BaseService<Sector> {
    Map<Object, Object> sectorStatusCount();
}

package com.sb.solutions.api.segments.subSegment.service;


import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface SubSegmentService extends BaseService<SubSegment> {
    Map<Object,Object> subSegmentStatusCount();

}

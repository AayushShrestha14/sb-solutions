package com.sb.solutions.api.segments.segment.service;

import com.sb.solutions.api.segments.segment.entity.Segment;
import com.sb.solutions.core.service.BaseService;

import java.util.Map;

public interface SegmentService extends BaseService<Segment> {
    Map<Object, Object> segmentStatusCount();
}

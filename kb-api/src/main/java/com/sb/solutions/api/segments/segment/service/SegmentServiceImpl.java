package com.sb.solutions.api.segments.segment.service;

import com.sb.solutions.api.segments.segment.entity.Segment;
import com.sb.solutions.api.segments.segment.repository.SegmentRepository;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SegmentServiceImpl implements SegmentService {

    private final SegmentRepository segmentRepository;
    @Override
    public List<Segment> findAll() {
        return segmentRepository.findAll();
    }

    @Override
    public Segment findOne(Long id) {
        return segmentRepository.getOne(id);
    }

    @Override
    public Segment save(Segment segment) {
        segment.setLastModified(new Date());
        if(segment.getId()==null){
            segment.setStatus(Status.ACTIVE);
        }
        return segmentRepository.save(segment);
    }

    @Override
    public Page<Segment> findAllPageable(Object segment, Pageable pageable) {
        Segment segmentMapped = (Segment) segment;
        return segmentRepository.segmentFilter(segmentMapped.getSegmentName()==null?"":segmentMapped.getSegmentName(),pageable);
    }

    @Override
    public Map<Object, Object> segmentStatusCount() {
        return segmentRepository.segmentStatusCount();
    }
}

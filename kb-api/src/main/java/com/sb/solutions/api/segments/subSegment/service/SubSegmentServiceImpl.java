package com.sb.solutions.api.segments.subSegment.service;

import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import com.sb.solutions.api.segments.subSegment.repository.SubSegmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SubSegmentServiceImpl implements SubSegmentService {
    private SubSegmentRepository subSegmentRepository;

    @Override
    public List<SubSegment> findAll() {
        return subSegmentRepository.findAll();
    }

    @Override
    public SubSegment findOne(Long id) {
        return subSegmentRepository.getOne(id);
    }

    @Override
    public SubSegment save(SubSegment subSegment) {
        return subSegmentRepository.save(subSegment);
    }

    @Override
    public Page<SubSegment> findAllPageable(Object subSegment, Pageable pageable) {
        SubSegment subSegmentMapped = (SubSegment) subSegment;
        return subSegmentRepository.subSegmentFilter(subSegmentMapped.getSubSegmentName()==null?"":subSegmentMapped.getSubSegmentName(),pageable);
    }

    @Override
    public Map<Object, Object> subSegmentStatusCount() {
        return subSegmentRepository.subSegmentStatusCount();
    }
}

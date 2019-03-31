package com.sb.solutions.api.segments.subSegment.repository;

import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface SubSegmentRepository extends JpaRepository<SubSegment,Long> {
    @Query(value = "select\n" +
            "  (select  count(id) from Sub_Segment where status=1) active,\n" +
            "(select  count(id) from Sub_Segment where status=0) inactive,\n" +
            "(select  count(id) from Sub_Segment) segments\n",nativeQuery = true)
    Map<Object,Object> subSegmentStatusCount();

    @Query(value = "select s from SubSegment s where s.subSegmentName like concat(:subSegmentName,'%')")
    Page<SubSegment> subSegmentFilter(@Param("subSegmentName")String subSegmentName, Pageable pageable);
}

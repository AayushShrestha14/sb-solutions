package com.sb.solutions.api.segments.subSegment.entity;

import com.sb.solutions.api.segments.segment.entity.Segment;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubSegment extends AbstractBaseEntity<Long> {
    private String subSegmentName;

    private boolean isFunded;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "segment_id", nullable = false)
    private Segment segment;

    private Status status;
}

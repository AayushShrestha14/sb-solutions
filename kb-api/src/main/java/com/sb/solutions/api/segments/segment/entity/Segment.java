package com.sb.solutions.api.segments.segment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.segments.subSegment.entity.SubSegment;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Segment extends BaseEntity<Long> {

    private String segmentName;

    private Status status;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "segment")
    private SubSegment subSegment;
}

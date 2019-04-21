package com.sb.solutions.api.sector.subsector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.sector.sector.entity.Sector;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubSector  extends BaseEntity<Long> {

    private String subSectorName;

    private String subSectorCode;

    private Status status;
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

}

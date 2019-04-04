package com.sb.solutions.api.sector.subsector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.sector.sector.entity.Sector;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubSector  extends AbstractBaseEntity<Long> {

    private String subSectorName;

    private String subSectorCode;

    private Status status;
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

}
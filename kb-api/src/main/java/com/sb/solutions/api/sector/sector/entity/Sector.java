package com.sb.solutions.api.sector.sector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.sector.subsector.entity.SubSector;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sector extends BaseEntity<Long> {
    private String sectorName;
    private String sectorCode;
    private Status status;
    @JsonIgnore
    @OneToMany(mappedBy = "sector")
    private Set<SubSector> subSectors;
}

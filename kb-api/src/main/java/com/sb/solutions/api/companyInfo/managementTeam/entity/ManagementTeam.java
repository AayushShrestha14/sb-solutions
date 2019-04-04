package com.sb.solutions.api.companyInfo.managementTeam.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementTeam extends AbstractBaseEntity<Long> {
    private String name;

    private String designation;
    @Column(name = "entityInfo_id")
    private long entityInfoId;

}
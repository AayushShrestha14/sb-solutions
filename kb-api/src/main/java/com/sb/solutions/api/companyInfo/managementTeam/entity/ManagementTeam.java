package com.sb.solutions.api.companyInfo.managementTeam.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ManagementTeam extends BaseEntity<Long> {
    private String name;
    private String description = "test";
    private String designation;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "entity_info_id")
//    private EntityInfo entityInfo;

}

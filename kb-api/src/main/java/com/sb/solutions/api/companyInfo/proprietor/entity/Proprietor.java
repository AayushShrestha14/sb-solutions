package com.sb.solutions.api.companyInfo.proprietor.entity;

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
public class Proprietor extends BaseEntity<Long> {

    private String name;

    private String contactNo;

    private String address;

    private double share;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "entity_info_id")
//    private EntityInfo entityInfo;
}

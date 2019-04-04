package com.sb.solutions.api.companyInfo.proprietor.entity;

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
public class Proprietor extends AbstractBaseEntity<Long> {

    private String name;

    private String contactNo;

    private String address;

    private double share;
    @Column(name = "entityInfo_id")
    private long entityInfoId;
}

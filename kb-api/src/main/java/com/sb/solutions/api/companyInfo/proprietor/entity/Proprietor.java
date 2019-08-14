package com.sb.solutions.api.companyInfo.proprietor.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Proprietor extends BaseEntity<Long> {

    private String name;

    private String contactNo;

    @ManyToOne
    private Province province;

    @ManyToOne
    private District district;

    @ManyToOne
    private MunicipalityVdc municipalityVdc;

    private double share;
}

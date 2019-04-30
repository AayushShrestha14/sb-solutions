package com.sb.solutions.api.valuator.entity;

import com.sb.solutions.api.address.district.entity.District;
import com.sb.solutions.api.address.municipalityVdc.entity.MunicipalityVdc;
import com.sb.solutions.api.address.province.entity.Province;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Valuator extends BaseEntity<Long> {
    private String name;
    private String contactNo;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
    @ManyToOne
    @JoinColumn(name = "municipality_vdc_id")
    private MunicipalityVdc municipalityVdc;
    private String streetName;
    private String wardNumber;
    @ManyToOne
    @JoinColumn(name = "site_province_id")
    private Province siteProvince;
    @ManyToOne
    @JoinColumn(name = "site_district_id")
    private District siteDistrict;
    @ManyToOne
    @JoinColumn(name = "site_municipality_vdc_id")
    private MunicipalityVdc siteMunicipalityVdc;
    private String siteStreetName;
    private String siteWardNumber;
}

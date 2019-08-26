package com.sb.solutions.api.siteVisit.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SiteVisit extends BaseEntity<Long> {

    private String path;

    @Transient
    private Object data;

//    private Boolean hasCurrentResident;
//
//    private Boolean hasBusinessSiteVisit;
//
//    private Boolean hasFixedAssetsCollateral;
//
//    private Boolean hasCurrentAssetsInspection;
//
//    private CurrentResident currentResident;
//
//    private BusinessSiteVisit businessSiteVisit;
//
//    private FixedAssetsCollateral fixedAssetsCollateral;
//
//    private AssetsInspection assetsInspection;
//
//    private CurrentAssetsInspection currentAssetsInspection;
//
//    private ReceivablePayableAssetsInspection receivablePayableAssetsInspection;

}

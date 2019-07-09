package com.sb.solutions.api.siteVisit.entity;

import javax.persistence.Entity;

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

    private Boolean hasCurrentResident;

    private Boolean hasBusinessSiteVisit;

    private Boolean hasFixedAssetsCollateral;

    private Boolean hasCurrentAssetsInspection;

    private BusinessSiteVisit businessSiteVisit;

    private AssetsInspection assetsInspection;

    private ReceivablePayableAssetsInspection receivablePayableAssetsInspection;

    private FixedAssetsCollateral fixedAssetsCollateral;

    private CurrentResident currentResident;

    private CurrentAssetsInspection currentAssetsInspection;

}

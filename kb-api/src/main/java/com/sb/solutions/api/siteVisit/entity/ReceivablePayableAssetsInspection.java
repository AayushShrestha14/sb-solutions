package com.sb.solutions.api.siteVisit.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivablePayableAssetsInspection {

    private List<PartyInfo> partyInfoList;

    private double threeMonthTotal;

    private double sixMonthTotal;

    private double oneYearTotal;

    private double moreThanOneYearTotal;

    private String findingAndComments;

    private List<ReceivableCurrentAssets> receivableCurrentAssetsList;

    private List<PayableCurrentAssets> payableCurrentAssetsList;

    private List<BankExposureAssets> bankExposureAssetsList;

    private List<InspectingStaff> inspectingStaffList;

    private String overallFindingAndCommentsOfCAI;

    private String findingsAndCommentsForCurrentAssetsInspection;

}

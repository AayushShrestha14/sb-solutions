package com.sb.solutions.api.siteVisit.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedAssetsCollateral {

    @JsonProperty("Fixed Assets Collateral")
    private String categoryName = "Fixed Assets Collateral";

    @JsonProperty("Site Visit Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @JsonProperty("Site Address")
    private String address;

    @JsonProperty("Name of Person Contacted")
    private String nameOfPersonContacted;

    @JsonProperty("Person Contacted Phone Number")
    private String personContactedPhone;

    @JsonProperty("Road Approach")
    private String roadApproach;

    @JsonProperty("Road Width")
    private Double roadWidth;

    @JsonProperty("Prominent Place")
    private String prominentPlace;

    @JsonProperty("Approach Distance")
    private Double approachDistance;

    //Value of particulars
    @JsonProperty("Tentative Value Per Area")
    private Double tentativeValuePerArea;

    @JsonProperty("Total Market Value of Land")
    private Double totalMarketValueOfLand;

    @JsonProperty("Tentative Market Value Per Square")
    private Double tentativeMarketValuePerSquareFeetOfBid;

    @JsonProperty("Total Market Value of Building")
    private Double totalMarketValueOfBuilding;

    @JsonProperty("Possible Deduction")
    private Double possibleDeduction;

    @JsonProperty("Approximate Market Value")
    private Double approximateMarketValue;

    //Others facility
    @JsonProperty("Water Supply")
    private Boolean waterSupply;

    @JsonProperty("Electricity")
    private Boolean electricity;

    @JsonProperty("Boundry Wall Construction")
    private Boolean boundryWallConstruction;

    @JsonProperty("Boundry Fencing")
    private Boolean boundryFencing;

    @JsonProperty("Drainage")
    private Boolean drainage;

    @JsonProperty("Open")
    private Boolean open;

    @JsonProperty("Remarks For Other Facility")
    private String remarksForOtherFacility;

    //Vicinity to the basic amenities
    @JsonProperty("Major Market Places Distance")
    private Double majorMarketplacesDistance;

    @JsonProperty("School College Distance")
    private Double schoolCollegeDistance;

    @JsonProperty("Hospital Nurshing Home Distance")
    private Double hospitalNursingHomeDistance;

    @JsonProperty("Electricity Line Distance")
    private Double electricityLineDistance;

    @JsonProperty("Telephone Line Distance")
    private Double telephoneLineDistance;

    @JsonProperty("Water Pipeline Distance")
    private Double waterPipelineDistance;

    @JsonProperty("Building")
    private Boolean building;

    //Quality of construction
    @JsonProperty("Building Area")
    private Double buildingArea;

    @JsonProperty("Date of Building Construction")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBuildingConstruction;

    @JsonProperty("Load Bearing Wall")
    private Boolean loadBearingWall;

    @JsonProperty("Motar Cement")
    private Boolean mortarCement;

    @JsonProperty("Other Roofing")
    private Boolean otherRoofing;

    @JsonProperty("Inside Furniture")
    private Boolean insideFurniture;

    @JsonProperty("Frame Structure")
    private Boolean frameStructure;

    @JsonProperty("RCC Roofing")
    private Boolean rccRoofing;

    @JsonProperty("Bathroom Toilet")
    private Boolean bathroomToilet;

    @JsonProperty("Remarks for Quality of Construction")
    private String qualityOfConstructionRemarks;

    @JsonProperty("Comments About FAC")
    private String commentsAboutFAC;

    @JsonProperty("Inspecting Staff")
    private List<InspectingStaff> inspectingStaffList;

    @JsonProperty("Branch Incharge Comment")
    private String branchInchargeComment;
}


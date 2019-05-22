package com.sb.solutions.api.buildingSecurity.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingSecurity extends BaseEntity<Long> {
    private String name;
    private String description;
    private double buildUpArea;
    private double buildUpAreaRate;
    private double totalCost;
    private String floorName;
    private double valuationArea;
    private double ratePerSquareFeet;
    private double estimatedCost;
    private String waterSupply;
    private String sanitation;
    private String electrification;
    private double buildingTotalCost;
    private double fairMarketValue;
    private double distressValue;
    private String detailDescription;
}

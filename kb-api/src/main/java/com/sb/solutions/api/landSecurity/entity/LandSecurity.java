package com.sb.solutions.api.landSecurity.entity;

import java.util.List;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandSecurity extends BaseEntity<Long> {

    private String ownerName;
    private String location;
    private double plotNumber;
    private List<String> areaFormat;
    private double area;
    private double fairMarketValue;
    private double distressValue;
    private String description;
}

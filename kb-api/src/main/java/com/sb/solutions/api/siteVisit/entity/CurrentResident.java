package com.sb.solutions.api.siteVisit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentResident {

    private String residentAddress;

    private String residentHouseNo;

    private String residentStreetName;

    private String residentNearby;

    private String residentOwnerName;

}

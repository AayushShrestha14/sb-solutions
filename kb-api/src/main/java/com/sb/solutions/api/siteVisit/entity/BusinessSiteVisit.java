package com.sb.solutions.api.siteVisit.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSiteVisit {

    private String officeAddress;

    private String personContactedName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfVisit;

    private String objectiveOfVisit;

    private String observation;

    private String location;

    private String latitude;

    private String longitude;

    private List<String> visitedBy;

    private String conclusion;

    private String comments;

    private String embedLocationLink;
}

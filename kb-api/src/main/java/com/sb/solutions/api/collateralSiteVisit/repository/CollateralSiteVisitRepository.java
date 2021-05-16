package com.sb.solutions.api.collateralSiteVisit.repository;

import com.sb.solutions.api.collateralSiteVisit.entity.CollateralSiteVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Mohammad Hussain on May, 2021
 */
public interface CollateralSiteVisitRepository extends JpaRepository<CollateralSiteVisit, Long> {

    List<CollateralSiteVisit> findCollateralSiteVisitBySecurityNameAndSecurity_Id(String securityName, Long Id);

    CollateralSiteVisit findCollateralSiteVisitBySiteVisitDateAndId(LocalDate siteVisitDate, Long Id);
}

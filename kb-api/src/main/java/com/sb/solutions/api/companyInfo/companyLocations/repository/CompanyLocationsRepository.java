package com.sb.solutions.api.companyInfo.companyLocations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.companyInfo.companyLocations.entity.CompanyLocations;

public interface CompanyLocationsRepository extends JpaRepository<CompanyLocations,Long> {

}

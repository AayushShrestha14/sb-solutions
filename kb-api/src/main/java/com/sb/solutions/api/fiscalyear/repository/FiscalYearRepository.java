package com.sb.solutions.api.fiscalyear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.fiscalyear.entity.FiscalYear;

@Repository
public interface FiscalYearRepository extends JpaRepository<FiscalYear , Long> {

    @Modifying
    @Query("update FiscalYear f set f.isCurrentYear = false ")
    void deActivePreviousFiscalYear();
}

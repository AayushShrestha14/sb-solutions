package com.sb.solutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.repository.BaseRepository;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/
public interface CustomerCadRepository extends
    BaseRepository<CustomerApprovedLoanCadDocumentation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CustomerApprovedLoanCadDocumentation  c set c.cadStageList= :previousList,c.docStatus=:docStatus , c.cadCurrentStage = :cadStage WHERE c.id = :id")
    void updateAction(@Param("id") Long id, @Param("docStatus") DocStatus docStatus,
        @Param("cadStage") CadStage cadStage, @Param("previousList") String previousList);

    @Query( value= "SELECT c.assignedLoan FROM CustomerApprovedLoanCadDocumentation c")
    List<CustomerLoan> findAllAssignedLoan();
}

package com.sb.solutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sb.solutions.core.repository.BaseRepository;
import com.sb.solutions.dto.CadAssignedLoanDto;
import com.sb.solutions.entity.CadStage;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.enums.CadDocStatus;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/
public interface CustomerCadRepository extends
    BaseRepository<CustomerApprovedLoanCadDocumentation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CustomerApprovedLoanCadDocumentation  c set c.cadStageList= :previousList,c.docStatus=:docStatus , c.cadCurrentStage = :cadStage WHERE c.id = :id")
    void updateAction(@Param("id") Long id, @Param("docStatus") CadDocStatus docStatus,
        @Param("cadStage") CadStage cadStage, @Param("previousList") String previousList);

    @Query(value = "SELECT assigned_loan_id from assigned_loan", nativeQuery = true)
    List<Long> findAllAssignedLoanIds();

    @Query(value = "SELECT new com.sb.solutions.dto.CadAssignedLoanDto(a.currentStage,a.loanType,c.id,a.id,a.proposal,a.loan.name,a.loan.id,a.previousStageList) FROM CustomerApprovedLoanCadDocumentation c inner join c.assignedLoan a  where c.id in (:ids)")
    List<CadAssignedLoanDto> findAssignedLoanByIdIn(@Param("ids") List<Long> ids);
}

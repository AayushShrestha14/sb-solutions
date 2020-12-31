package com.sb.solutions.service;

import com.sb.solutions.dto.CadStageDto;
import com.sb.solutions.dto.LoanHolderDto;
import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

public interface LoanHolderService {

    Page<LoanHolderDto> getAllUnAssignLoanForCadAdmin(Map<String, String> filterParams, Pageable pageable);

    @Transactional
    String  assignLoanToUser(CadStageDto cadStageDto);

    String cadAction(CadStageDto cadStageDto);

    Page<CustomerApprovedLoanCadDocumentation> getAllByFilterParams(Map<String, String> filterParams, Pageable pageable);
}

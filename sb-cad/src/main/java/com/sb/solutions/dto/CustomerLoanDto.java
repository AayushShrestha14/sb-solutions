package com.sb.solutions.dto;

import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.stage.entity.Stage;
import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.DocStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Rujan Maharjan on  12/1/2020
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanDto extends BaseDto<Long> {


    private Proposal proposal;

    private List<LoanStageDto> distinctPreviousList;

    private List previousList;

    private DocStatus documentStatus;

    private LoanStage currentStage;

    private LoanConfig loan;
}

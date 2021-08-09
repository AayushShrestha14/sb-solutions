package com.sb.solutions.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;

/**
 * @author : Rujan Maharjan on  8/8/2021
 **/
@Data
public class CadAssignedLoanDto {

    private Proposal proposal;

    private List<LoanStageDto> distinctPreviousList;

    private List previousList;

    private DocStatus documentStatus;

    private LoanStage currentStage;

    private LoanConfig loan;

    private LoanType loanType;

    private Long cadId;

    private Long customerLoanId;

    private String loanName;

    private Long loanId;

    private List<CustomerLoan>  assigned;

    private String previousStageList;

    public CadAssignedLoanDto(
        LoanStage currentStage, LoanType loanType, Long cadId, Long customerLoanId,
        Proposal proposal, String loanName, Long loanId,String previousStageList) {
        this.currentStage = currentStage;
        this.loanType = loanType;
        this.cadId = cadId;
        this.customerLoanId = customerLoanId;
        this.proposal = proposal;
        this.loanName = loanName;
        this.loanId = loanId;
        this.previousStageList = previousStageList;
    }

    public LoanConfig getLoan(){
        LoanConfig loanConfig = new LoanConfig();
        loanConfig.setId(this.loanId);
        loanConfig.setName(this.loanName);
        return loanConfig;
    }

    public static List<CustomerLoan>  getAssigned(List<CadAssignedLoanDto> cadAssignedLoanDto,
        CustomerInfo customerInfo){
        List<CustomerLoan> customerLoanList = new ArrayList<>();
            cadAssignedLoanDto.forEach(c -> {
                CustomerLoan customerLoan = new CustomerLoan();
                customerLoan.setLoanHolder(customerInfo);
                customerLoan.setId(c.getCustomerLoanId());
                customerLoan.setProposal(c.getProposal());
                customerLoan.setLoan(c.getLoan());
                customerLoan.setLoanType(c.getLoanType());
                customerLoan.setCurrentStage(c.getCurrentStage());
                customerLoan.setPreviousStageList(c.getPreviousStageList());
                customerLoanList.add(customerLoan);
            });
            return customerLoanList;
    }
}

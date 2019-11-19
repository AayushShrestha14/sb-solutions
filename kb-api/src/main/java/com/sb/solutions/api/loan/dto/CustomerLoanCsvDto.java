package com.sb.solutions.api.loan.dto;

import java.time.LocalDate;

import lombok.Data;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;

/**
 * @author yunish on 11/12/2019
 */
@Data
public class CustomerLoanCsvDto {

    private Customer customerInfo;

    private LoanConfig loan;

    private CompanyInfo companyInfo;

    private LoanType loanType = LoanType.NEW_LOAN;

    private DocStatus documentStatus = DocStatus.PENDING;

    private LoanApprovalType loanCategory;

    private LoanStage currentStage;

    private Branch branch;

    private User toUser;

    private Role toRole;

    private Proposal proposal;

    private long loanSpan;

    private long loanPendingSpan;

    private long loanPossession;

    private LocalDate createdAt;

}

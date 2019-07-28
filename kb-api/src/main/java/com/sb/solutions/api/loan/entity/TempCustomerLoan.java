package com.sb.solutions.api.loan.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.Priority;

@Entity
@Data
@Table(name = "customer_loan")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TempCustomerLoan extends BaseEntity<Long> {

    @ManyToOne(cascade = {
        CascadeType.MERGE})
    private Customer customerInfo;

    @OneToOne(cascade = {
        CascadeType.MERGE})
    private LoanConfig loan;

    @ManyToOne(cascade = {
        CascadeType.MERGE})
    private EntityInfo entityInfo;

    private LoanType loanType;

    private DocStatus documentStatus = DocStatus.PENDING;

    @ManyToOne(cascade = {
        CascadeType.MERGE})
    private DmsLoanFile dmsLoanFile;

    @OneToOne(cascade = CascadeType.ALL)
    private LoanStage currentStage;

    private Priority priority;


    @OneToOne(cascade = {
        CascadeType.MERGE})
    private Branch branch;

    private String offerLetterUrl;

    @OneToOne(cascade = {
        CascadeType.MERGE})
    private Proposal proposal;

    @Lob
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String previousStageList;

    private Boolean isValidated = false;


}

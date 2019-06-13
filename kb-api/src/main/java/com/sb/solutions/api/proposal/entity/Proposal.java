package com.sb.solutions.api.proposal.entity;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.RepaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Proposal extends BaseEntity<Long> {

    @OneToOne
    private LoanConfig creditFacility;
    private int proposedLimit;
    private double interestRate;
    private double baseRate;
    private double premiumRateOnBaseRate;
    private boolean percentOrFlat;
    private double serviceCharge;
    private Date tenureYear;
    private double cibCharge;
    private RepaymentMode repaymentMode;
    private String purposeOfSubmission;
    private String disbursementCriteria;
    private String creditInformationReportStatus;
    private String incomeFromTheAccount;
    private String borrowerInformation;


}

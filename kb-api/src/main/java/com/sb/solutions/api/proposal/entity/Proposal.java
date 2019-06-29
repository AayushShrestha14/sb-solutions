package com.sb.solutions.api.proposal.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.RepaymentMode;
import com.sb.solutions.core.enums.ServiceChargeMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Proposal extends BaseEntity<Long> {

    private double proposedLimit;
    private double interestRate;
    private double baseRate;
    private double premiumRateOnBaseRate;
    private ServiceChargeMethod serviceChargeMethod;
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

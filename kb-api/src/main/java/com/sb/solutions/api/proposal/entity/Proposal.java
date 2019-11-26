package com.sb.solutions.api.proposal.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.RepaymentMode;
import com.sb.solutions.core.enums.ServiceChargeMethod;
import com.sb.solutions.core.utils.NumberToWordsConverter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Proposal extends BaseEntity<Long> {
//    private String path;
    private String data;
    private BigDecimal proposedLimit;
//    private double interestRate;
//    private double baseRate;
//    private double premiumRateOnBaseRate;
//    private ServiceChargeMethod serviceChargeMethod;
//    private double serviceCharge;
    private double tenureDurationInMonths;
//    private double cibCharge;
//    private RepaymentMode repaymentMode;
//    private String purposeOfSubmission;
//    private String disbursementCriteria;
//    private String creditInformationReportStatus;
//    private String incomeFromTheAccount;
//    private String borrowerInformation;
    @Transient
    private String proposedAmountInWords;

    public String getProposedAmountInWords() {
        try {
            return NumberToWordsConverter
                .calculateAmountInWords(String.valueOf(this.getProposedLimit()));
        } catch (Exception e) {
            return null;
        }
    }
}

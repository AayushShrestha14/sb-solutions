package com.sb.solutions.api.insurance.entity;

import java.util.Date;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author Elvin Shrestha on 4/19/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InsuranceHistory extends BaseEntity<Long> {

    private Long customerLoanId;
    private String company;
    private double insuredAmount;
    private double premiumAmount;
    private Date issuedDate;
    private Date expiryDate;
    private String policyType;
}

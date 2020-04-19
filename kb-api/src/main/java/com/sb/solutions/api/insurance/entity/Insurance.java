package com.sb.solutions.api.insurance.entity;

import java.util.Date;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author Aashish shrestha on 6th. March, 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Insurance extends BaseEntity<Long> {

    private String company;

    private double insuredAmount;

    private double premiumAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issuedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    private String policyType;

    private String remarks;
}

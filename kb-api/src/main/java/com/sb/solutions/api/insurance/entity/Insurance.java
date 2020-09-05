package com.sb.solutions.api.insurance.entity;

import java.util.Date;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.utils.string.NameFormatter;
import com.sb.solutions.core.utils.string.WordFormatter;

/**
 * @author Aashish shrestha on 6th. March, 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Insurance extends BaseEntity<Long> {

    @JsonDeserialize(using = NameFormatter.class)
    private String company;

    private double insuredAmount;

    private double premiumAmount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date issuedDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expiryDate;

    @JsonDeserialize(using = WordFormatter.class)
    private String policyType;

    private String remarks;

    private String policyNumber;
}

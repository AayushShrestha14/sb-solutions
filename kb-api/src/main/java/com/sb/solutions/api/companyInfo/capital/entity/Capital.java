package com.sb.solutions.api.companyInfo.capital.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capital extends AbstractBaseEntity<Long> {

    private double authorizedCapital;

    private double paidUpCapital;

    private double issuedCapital;

    private double totalCapital;

    private double fixedCapital;

    private double workingCapital;

    private int numberOfShareholder;
}

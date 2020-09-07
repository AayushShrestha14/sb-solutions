package com.sb.solutions.api.loan;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.customer.entity.CustomerInfo;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerLoanGroupDto {

    private CustomerInfo loanHolder;

    private BigDecimal totalObtainedLimit;
}

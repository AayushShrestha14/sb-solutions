package com.sb.solutions.api.loan;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.loan.entity.CustomerLoan;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerLoanGroupDto {

    private CustomerInfo loanHolder;

    private BigDecimal totalApprovedLimit;

    private BigDecimal totalPendingLimit;

    private List<CustomerLoan> customerLoans;

}

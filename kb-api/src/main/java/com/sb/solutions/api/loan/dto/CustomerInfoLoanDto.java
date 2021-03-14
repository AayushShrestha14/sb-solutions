package com.sb.solutions.api.loan.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.loan.entity.CustomerLoan;

/**
 * @author : Rujan Maharjan on  3/11/2021
 **/

@Data
@NoArgsConstructor
public class CustomerInfoLoanDto {

    private CustomerInfo customerInfo;

    private List<CustomerLoan> loanSingleList = new ArrayList<>();
    private List<Map<Long, List<CustomerLoan>>> combineList = new ArrayList<>();

    private Long totalLoan = 0L;

    public CustomerInfoLoanDto(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public CustomerInfoLoanDto(CustomerInfo customerInfo,
        List<CustomerLoan> loanSingleList, Long totalLoan) {
        this.customerInfo = customerInfo;
        this.loanSingleList = loanSingleList;
        this.totalLoan = totalLoan;
    }

    public Long getTotalLoan() {
        Long i = (long) this.combineList.size();
        return ((long) this.getLoanSingleList().size()) + i;
    }
}

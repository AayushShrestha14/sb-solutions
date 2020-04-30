package com.sb.solutions.api.loanflag.service;

import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.core.enums.LoanFlag;
import com.sb.solutions.core.service.Service;

/**
 * @author Elvin Shrestha on 4/28/2020
 */
public interface CustomerLoanFlagService extends Service<CustomerLoanFlag, Long> {
    CustomerLoanFlag findCustomerLoanFlagByFlagAndCustomerLoanId(LoanFlag flag, Long loanId);

    void deleteCustomerLoanFlagById(Long id);
}

package com.sb.solutions.api.loanflag.repository;

import org.springframework.stereotype.Repository;

import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.core.enums.LoanFlag;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author Elvin Shrestha on 4/28/2020
 */
@Repository
public interface CustomerLoanFlagRepository extends BaseRepository<CustomerLoanFlag, Long> {
    CustomerLoanFlag findCustomerLoanFlagByFlagAndCustomerLoanId(LoanFlag flag, Long loanId);

    void deleteCustomerLoanFlagById(Long id);
}

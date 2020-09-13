package com.sb.solutions.api.loanflag.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author Elvin Shrestha on 4/28/2020
 */
@Repository
public interface CustomerLoanFlagRepository extends BaseRepository<CustomerLoanFlag, Long> {

    void deleteCustomerLoanFlagById(Long id);

    List<CustomerLoanFlag> findAllByCustomerInfoId(Long id);
}

package com.sb.solutions.api.Loan.service;

import com.sb.solutions.api.Loan.entity.Loan;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface LoanService extends BaseService<Loan> {

    void sendForwardBackwardLoan(Loan loan);
}

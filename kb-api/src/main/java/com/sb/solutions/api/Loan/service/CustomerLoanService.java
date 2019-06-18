package com.sb.solutions.api.Loan.service;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanService extends BaseService<CustomerLoan> {

    void sendForwardBackwardLoan(CustomerLoan customerLoan);

    Map<String, Integer> statusCount();

    List<CustomerLoan> getFirst5CustomerLoanByDocumentStatus(DocStatus status);


    List<Map<String, Double>> proposedAmount();

    List<Map<String, Double>> proposedAmountByBranch(Long branchId);

}

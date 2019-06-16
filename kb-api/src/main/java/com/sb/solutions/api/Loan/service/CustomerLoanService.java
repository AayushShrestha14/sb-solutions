package com.sb.solutions.api.Loan.service;

import java.util.List;
import java.util.Map;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanService extends BaseService<CustomerLoan> {

    void sendForwardBackwardLoan(CustomerLoan customerLoan);

    Map<Object, Object> statusCount();

    List<CustomerLoan> getCustomerLoanByDocumentStatus(DocStatus status);
}

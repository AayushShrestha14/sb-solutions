package com.sb.solutions.api.loan.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanService extends BaseService<CustomerLoan> {

    void sendForwardBackwardLoan(CustomerLoan customerLoan);

    Map<String, Integer> statusCount();

    List<CustomerLoan> getFirst5CustomerLoanByDocumentStatus(DocStatus status);


    List<PieChartDto> proposedAmount();

    List<PieChartDto> proposedAmountByBranch(Long branchId);

    List<CustomerLoan> getByCitizenshipNumber(String citizenshipNumber);

    List<CustomerLoan> getByRegistrationNumber(String registrationNumber);

    Page<CustomerLoan> getCatalogues(Object searchDto, Pageable pageable);

    CustomerLoan delCustomerLoan(Long id);

    List<StatisticDto> getStats(Long branchId);

    Map<String, String> chkUserContainCustomerLoan(Long id);

    CustomerLoan renewCloseEntity(CustomerLoan object);


}

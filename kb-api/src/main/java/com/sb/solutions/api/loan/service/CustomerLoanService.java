package com.sb.solutions.api.loan.service;

import java.text.ParseException;
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


    List<PieChartDto> proposedAmount(String startDate, String endDate) throws ParseException;

    List<PieChartDto> proposedAmountByBranch(Long branchId, String startDate, String endDate)
        throws ParseException;

    List<CustomerLoan> getByCitizenshipNumber(String citizenshipNumber);

    Page<CustomerLoan> getCatalogues(Object searchDto, Pageable pageable);

    CustomerLoan delCustomerLoan(Long id);

    List<StatisticDto> getStats(Long branchId, String startDate, String endDate)
        throws ParseException;

    Map<String, String> chkUserContainCustomerLoan(Long id);

    CustomerLoan renewCloseEntity(CustomerLoan object);

    String csv(Object searchDto);


}

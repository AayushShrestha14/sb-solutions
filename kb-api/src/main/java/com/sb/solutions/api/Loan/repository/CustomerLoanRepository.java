package com.sb.solutions.api.Loan.repository;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>, JpaSpecificationExecutor<CustomerLoan> {


    List<CustomerLoan> findFirst5ByDocumentStatusOrderByIdDesc(DocStatus status);
//    Map<Object, Object> proposedAmount();
}

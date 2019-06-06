package com.sb.solutions.api.Loan.repository;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan,Long> {


    List<CustomerLoan> findFirst5ByDocumentStatusOrderByIdDesc(DocStatus status);

}

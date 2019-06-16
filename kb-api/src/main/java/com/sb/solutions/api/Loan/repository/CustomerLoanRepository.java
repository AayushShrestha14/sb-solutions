package com.sb.solutions.api.Loan.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>,
    JpaSpecificationExecutor<CustomerLoan> {


    List<CustomerLoan> findFirst5ByDocumentStatusOrderByIdDesc(DocStatus status);

    @Query(value = "select\n" +
        "(select  count(id) from customer_loan where document_status=0) pending,\n" +
        "(select  count(id) from customer_loan where document_status=1) Approved,\n" +
        "(select  count(id) from customer_loan where document_status=2) Rejected,\n" +
        "(select  count(id) from customer_loan where document_status=3) Closed,\n" +
        "(select  count(id) from customer_loan) total\n", nativeQuery = true)
    Map<Object, Object> statusCount();

}

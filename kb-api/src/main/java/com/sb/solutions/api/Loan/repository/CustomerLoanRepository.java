package com.sb.solutions.api.Loan.repository;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>, JpaSpecificationExecutor<CustomerLoan> {


    List<CustomerLoan> findFirst5ByDocumentStatusAndCurrentStageToRoleIdOrderByIdDesc(DocStatus status, Long currentStageUserId);

    @Query(value = "select\n" +
            "(select  count(id) from customer_loan where document_status=0) pending,\n" +
            "(select  count(id) from customer_loan where document_status=1) Approved,\n" +
            "(select  count(id) from customer_loan where document_status=2) Rejected,\n" +
            "(select  count(id) from customer_loan where document_status=3) Closed,\n" +
            "(select  count(id) from customer_loan) total\n", nativeQuery = true)
    Map<Object, Object> statusCount();

    @Query(value = "select name, sum(proposed_amount) AS value from customer_loan c" +
            " join dms_loan_file d on c.dms_loan_file_id = d.id" +
            " join loan_config l on c.loan_id=l.id " +
            "GROUP BY c.loan_id", nativeQuery = true)
    List<Map<Object, Object>> proposedAmount();

    @Query(value = "select name, sum(proposed_amount) AS value from customer_loan c " +
            "join dms_loan_file d on c.dms_loan_file_id = d.id " +
            "join loan_config l on c.loan_id=l.id " +
            "where c.branch_id =:branchId GROUP BY c.loan_id", nativeQuery = true)
    List<Map<Object, Object>> proposedAmountByBranchId(Long branchId);
}

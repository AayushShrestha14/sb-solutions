package com.sb.solutions.api.loan.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>,
    JpaSpecificationExecutor<CustomerLoan> {

    List<CustomerLoan> findFirst5ByDocumentStatusAndCurrentStageToRoleIdAndBranchIdOrderByIdDesc(
        DocStatus status, Long currentStageRoleId, Long branchId);

    @Query(value = "SELECT "
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=0 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid)) pending,"
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl"
        + " LEFT JOIN loan_stage l ON l.id=cl.current_stage_id WHERE cl.document_status=1 "
        + "AND l.to_role_id IN (:id) AND cl.branch_id IN (:bid)) Approved,"
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl"
        + " LEFT JOIN loan_stage l ON l.id=cl.current_stage_id"
        + " WHERE cl.document_status=2 AND l.to_role_id IN (:id) AND cl.branch_id IN (:bid) ) Rejected,"
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl "
        + "LEFT JOIN loan_stage l ON l.id=cl.current_stage_id "
        + "WHERE cl.document_status=3 AND l.to_role_id IN (:id) AND cl.branch_id IN (:bid) ) Closed,"
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl"
        + " LEFT JOIN loan_stage l ON l.id=cl.current_stage_id WHERE l.to_role_id IN (:id) "
        + "AND cl.branch_id IN (:bid) )total", nativeQuery = true)
    Map<String, Integer> statusCount(@Param("id") Long id, @Param("bid") Long bid);

    @Query(value = "SELECT name, SUM(proposed_amount) AS value FROM customer_loan c"
        + " JOIN dms_loan_file d ON c.dms_loan_file_id = d.id"
        + " JOIN loan_config l ON c.loan_id=l.id "
        + "GROUP BY c.loan_id", nativeQuery = true)
    List<Map<String, Double>> proposedAmount();

    @Query(value = "SELECT name, SUM(proposed_amount) AS value FROM customer_loan c "
        + "JOIN dms_loan_file d ON c.dms_loan_file_id = d.id "
        + "JOIN loan_config l ON c.loan_id=l.id "
        + "WHERE c.branch_id =:branchId GROUP BY c.loan_id", nativeQuery = true)
    List<Map<String, Double>> proposedAmountByBranchId(@Param("branchId") Long branchId);
}

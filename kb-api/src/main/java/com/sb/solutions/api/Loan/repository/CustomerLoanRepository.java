package com.sb.solutions.api.Loan.repository;

import com.sb.solutions.api.Loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>, JpaSpecificationExecutor<CustomerLoan> {


    List<CustomerLoan> findFirst5ByDocumentStatusAndCurrentStageToRoleIdAndBranchIdOrderByIdDesc(DocStatus status, Long currentStageRoleId,Long branchId);

    @Query(value = "select\n" +
            "(select  count(cl.id) from customer_loan cl left join loan_stage l on l.id=cl.current_stage_id where cl.document_status=0 and l.to_role_id in (:id) and cl.branch_id in (:bid)) pending,\n" +
            "(select  count(cl.id) from customer_loan cl left join loan_stage l on l.id=cl.current_stage_id where cl.document_status=1 and l.to_role_id in (:id) and cl.branch_id in (:bid)) Approved,\n" +
            "(select  count(cl.id) from customer_loan cl left join loan_stage l on l.id=cl.current_stage_id where cl.document_status=3 and l.to_role_id in (:id) and cl.branch_id in (:bid) ) Rejected,\n" +
            "(select  count(cl.id) from customer_loan cl left join loan_stage l on l.id=cl.current_stage_id where cl.document_status=3 and l.to_role_id in (:id) and cl.branch_id in (:bid) ) Closed,\n" +
            "(select  count(cl.id) from customer_loan cl left join loan_stage l on l.id=cl.current_stage_id where l.to_role_id in (:id) and cl.branch_id in (:bid) )total\n", nativeQuery = true)
    Map<Object, Object> statusCount(@Param("id") Long id,@Param("bid") Long bid);

}

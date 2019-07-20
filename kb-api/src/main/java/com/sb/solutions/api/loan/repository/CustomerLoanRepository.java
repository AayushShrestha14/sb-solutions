package com.sb.solutions.api.loan.repository;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.solutions.api.loan.PieChartDto;
import com.sb.solutions.api.loan.StatisticDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.DocAction;
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

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=1 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid)) Approved,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=2 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) ) Rejected,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=3 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) ) Closed,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl"
        + " LEFT JOIN loan_stage l ON l.id=cl.current_stage_id WHERE l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) )total", nativeQuery = true)
    Map<String, Integer> statusCount(@Param("id") Long id, @Param("bid") List<Long> bid);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.dmsLoanFile.proposedAmount)) FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id IN (:branchId) GROUP BY c.loan.id,l.name")
    List<PieChartDto> proposedAmount(@Param("branchId") List<Long> branchId);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.dmsLoanFile.proposedAmount)) "
            + "FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id = :branchId GROUP BY c.loan.id,l.name")
    List<PieChartDto> proposedAmountByBranchId(@Param("branchId") Long branchId);

    List<CustomerLoan> getByCustomerInfoCitizenshipNumberOrDmsLoanFileCitizenshipNumber(
        String generalCitizenShipNumber, String dmsCitizenShipNumber);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name) FROM "
            + "CustomerLoan c WHERE c.branch.id = :branchId  GROUP BY c.loan.id, c.documentStatus")
    List<StatisticDto> getLasStatisticsByBranchId(@Param("branchId") Long branchId);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.dmsLoanFile.proposedAmount), "
            + "c.documentStatus, c.loan.name) FROM "
            + "CustomerLoan c WHERE c.branch.id = :branchId GROUP BY c.loan.id, c.documentStatus")
    List<StatisticDto> getDmsStatisticsByBranchId(@Param("branchId") Long branchId);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name) FROM "
            + "CustomerLoan c WHERE c.branch.id IN (:branchIds) GROUP BY c.loan.id, c.documentStatus")
    List<StatisticDto> getLasStatistics(@Param("branchIds") List<Long> branchIds);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.dmsLoanFile.proposedAmount), "
            + "c.documentStatus, c.loan.name) FROM "
            + "CustomerLoan c WHERE c.branch.id IN (:branchIds) GROUP BY c.loan.id, c.documentStatus")
    List<StatisticDto> getDmsStatistics(@Param("branchIds") List<Long> branchIds);

    @Modifying
    @Transactional
    void deleteByIdAndCurrentStageDocAction(Long id, DocAction docAction);
}

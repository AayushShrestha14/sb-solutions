package com.sb.solutions.api.loan.repository;

import java.util.Date;
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
import com.sb.solutions.core.enums.LoanFlag;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>,
    JpaSpecificationExecutor<CustomerLoan> {

    List<CustomerLoan> findFirst5ByDocumentStatusAndCurrentStageToRoleIdAndBranchIdOrderByIdDesc(
        DocStatus status, Long currentStageRoleId, Long branchId);

    @Query(value = "SELECT "
        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status  in(4,5,6,7)  AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) AND l.to_user_id=:uid) initial,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=0 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) AND l.to_user_id=:uid) pending,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=1"
        + " AND cl.branch_id IN (:bid)) Approved,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=2 AND l.to_role_id IN (:id)"
        + " AND cl.branch_id IN (:bid) ) Rejected,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl LEFT JOIN loan_stage l"
        + " ON l.id=cl.current_stage_id WHERE cl.document_status=3"
        + " AND cl.branch_id IN (:bid) ) Closed,"

        + "(SELECT  COUNT(cl.id) FROM customer_loan cl"
        + " LEFT JOIN loan_stage l ON l.id=cl.current_stage_id WHERE "
        + "  cl.branch_id IN (:bid) )total,"

        + "(SELECT COUNT(cl.id) FROM customer_loan cl WHERE cl.notify = 1 AND "
        + "cl.noted_by IS NULL) notify", nativeQuery = true)
    Map<String, Integer> statusCount(@Param("id") Long id, @Param("bid") List<Long> bid,
        @Param("uid") Long uid);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id IN (:branchId) GROUP BY c.loan.id,l.name")
    List<PieChartDto> proposedAmount(@Param("branchId") List<Long> branchId);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id IN (:branchId)  AND c.createdAt <= :endDate GROUP"
            + " BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountBefore(@Param("branchId") List<Long> branchId,
        @Param("endDate") Date endDate);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id IN (:branchId)  AND c.createdAt >= :startDate GROUP"
            + " BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountAfter(@Param("branchId") List<Long> branchId,
        @Param("startDate") Date startDate);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) FROM CustomerLoan c"
            + " join c.loan l WHERE c.branch.id IN (:branchId) AND (c.createdAt >= "
            + ":startDate AND c.createdAt <= :endDate) GROUP BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountBetween(@Param("branchId") List<Long> branchId,
        @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) "
            + "FROM CustomerLoan c join c.loan l WHERE c.branch.id = :branchId GROUP BY c.loan"
            + ".id, l.name")
    List<PieChartDto> proposedAmountByBranchId(@Param("branchId") Long branchId);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) "
            + "FROM CustomerLoan c join c.loan l WHERE c.branch.id = :branchId AND c.createdAt <="
            + " :endDate GROUP BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountByBranchIdAndDateBefore(@Param("branchId") Long branchId,
        @Param("endDate") Date endDate);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) "
            + "FROM CustomerLoan c JOIN c.loan l WHERE c.branch.id = :branchId AND c.createdAt >= "
            + ":startDate GROUP BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountByBranchIdAndDateAfter(@Param("branchId") Long branchId,
        @Param("startDate") Date startDate);

    @Query(value =
        "SELECT NEW com.sb.solutions.api.loan.PieChartDto(l.name,SUM(c.proposal.proposedLimit)) "
            + "FROM CustomerLoan c join c.loan l WHERE c.branch.id = :branchId AND (c.createdAt "
            + ">= :startDate AND c.createdAt <= :endDate) GROUP BY c.loan.id, l.name")
    List<PieChartDto> proposedAmountByBranchIdAndDateBetween(@Param("branchId") Long branchId,
        @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<CustomerLoan> getByCustomerInfoCitizenshipNumber(String citizenshipNumber1);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id = :branchId "
            + "GROUP BY c.loan.id, c.loan.name, c.documentStatus")
    List<StatisticDto> getLasStatisticsByBranchId(@Param("branchId") Long branchId);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id = :branchId "
            + "AND c.createdAt <= :endDate GROUP BY c.loan.id, c.loan.name, c.documentStatus")
    List<StatisticDto> getLasStatisticsByBranchIdAndDateBefore(@Param("branchId") Long branchId,
        @Param("endDate") Date endDate);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id = :branchId "
            + "AND c.createdAt >= :startDate GROUP BY c.loan.id, c.loan.name, c.documentStatus")
    List<StatisticDto> getLasStatisticsByBranchIdAndDateAfter(@Param("branchId") Long branchId,
        @Param("startDate") Date startDate);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), "
            + "c.documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id = :branchId "
            + "AND (c.createdAt >= :startDate AND c.createdAt <= :endDate) GROUP BY c.loan.id, c"
            + ".loan.name, c.documentStatus")
    List<StatisticDto> getLasStatisticsByBranchIdAndDateBetween(@Param("branchId") Long branchId,
        @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), c"
            + ".documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id IN "
            + "(:branchIds) GROUP BY c.loan.id, c.loan.name, c.documentStatus")
    List<StatisticDto> getLasStatistics(@Param("branchIds") List<Long> branchIds);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), c"
            + ".documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id IN "
            + "(:branchIds) AND c.createdAt <= :endDate GROUP BY c.loan.id, c.loan.name, c"
            + ".documentStatus")
    List<StatisticDto> getLasStatisticsAndDateBefore(@Param("branchIds") List<Long> branchIds,
        @Param("endDate") Date endDate);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), c"
            + ".documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id IN "
            + "(:branchIds) AND c.createdAt >= :startDate GROUP BY c.loan.id, c.loan.name, c"
            + ".documentStatus")
    List<StatisticDto> getLasStatisticsAndDateAfter(@Param("branchIds") List<Long> branchIds,
        @Param("startDate") Date startDate);

    @Query(
        "SELECT NEW com.sb.solutions.api.loan.StatisticDto(SUM(c.proposal.proposedLimit), c"
            + ".documentStatus, c.loan.name, COUNT(c)) FROM CustomerLoan c WHERE c.branch.id IN "
            + "(:branchIds) AND (c.createdAt >= :startDate AND c.createdAt <= :endDate) GROUP BY c.loan.id,"
            + " c.loan.name, c.documentStatus")
    List<StatisticDto> getLasStatisticsAndDateBetween(@Param("branchIds") List<Long> branchIds,
        @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Modifying
    @Transactional
    void deleteByIdAndCurrentStageDocAction(Long id, DocAction docAction);

    @Query("SELECT COUNT(c) FROM CustomerLoan c JOIN c.currentStage s"
        + " WHERE s.toUser.id = :id AND s.toRole.id=:rId AND c.documentStatus= :docStatus")
    Integer chkUserContainCustomerLoan(@Param("id") Long id, @Param("rId") Long rId,
        @Param("docStatus") DocStatus docStatus);

    @Modifying
    @Transactional
    @Query("UPDATE CustomerLoan c SET c.isCloseRenew = true ,c.childId = :cId  WHERE c.id = :id")
    void updateCloseRenewChildId(@Param("cId") Long cId, @Param("id") Long id);

    List<CustomerLoan> getCustomerLoanByCustomerInfoId(Long id);
}

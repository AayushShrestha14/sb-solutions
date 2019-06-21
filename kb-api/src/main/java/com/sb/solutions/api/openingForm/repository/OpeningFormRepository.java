package com.sb.solutions.api.openingForm.repository;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.core.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface OpeningFormRepository extends JpaRepository<OpeningForm, Long> {
    Page<OpeningForm> findAllByBranchAndStatus(Branch branch, Pageable pageable, AccountStatus accountStatus);
    @Query(value = "select\n" +
            "  (select  count(id) from opening_form where status=0 and opening_form.branch_id = :branchId) newed,\n" +
            "(select  count(id) from opening_form where status=1 and opening_form.branch_id = :branchId) approval,\n" +
            "(select  count(id) from opening_form where status=2 and opening_form.branch_id = :branchId) rejected,\n" +
            "(select  count(id) from opening_form where opening_form.branch_id = :branchId) total\n", nativeQuery = true)
    Map<Object, Object> openingFormStatusCount(Long branchId);
}

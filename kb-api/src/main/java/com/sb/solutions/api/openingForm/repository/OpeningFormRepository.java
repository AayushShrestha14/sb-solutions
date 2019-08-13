package com.sb.solutions.api.openingForm.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.core.enums.AccountStatus;

public interface OpeningFormRepository extends JpaRepository<OpeningForm, Long>,
    JpaSpecificationExecutor<OpeningForm> {

    Page<OpeningForm> findAllByBranchAndStatus(Branch branch, Pageable pageable,
        AccountStatus accountStatus);

    @Query(value = "select (select count(id)"
        + "        from opening_form"
        + "        where status = 0"
        + "          and opening_form.branch_id = :branchId) newed,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where status = 1"
        + "          and opening_form.branch_id = :branchId) approval,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where status = 2"
        + "          and opening_form.branch_id = :branchId) rejected,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where opening_form.branch_id = :branchId) total", nativeQuery = true)
    Map<Object, Object> openingFormStatusCount(Long branchId);
}

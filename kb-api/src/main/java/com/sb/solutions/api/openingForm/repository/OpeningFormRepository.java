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
            "  (select  count(id) from opening_form where status=0) newed,\n" +
            "(select  count(id) from opening_form where status=1) approval,\n" +
            "(select  count(id) from opening_form where status=2) rejected,\n" +
            "(select  count(id) from opening_form) total\n", nativeQuery = true)
    Map<Object, Object> openingFormStatusCount();
}

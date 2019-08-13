package com.sb.solutions.api.openingForm.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sb.solutions.api.openingForm.entity.OpeningForm;

public interface OpeningFormRepository extends JpaRepository<OpeningForm, Long>,
    JpaSpecificationExecutor<OpeningForm> {

    @Query(value = "select (select count(id)"
        + "        from opening_form"
        + "        where status = 0"
        + "          and opening_form.branch_id in (:branchIds)) newed,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where status = 1"
        + "          and opening_form.branch_id in (:branchIds)) approval,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where status = 2"
        + "          and opening_form.branch_id in (:branchIds)) rejected,"
        + "       (select count(id)"
        + "        from opening_form"
        + "        where opening_form.branch_id in (:branchIds)) total", nativeQuery = true)
    Map<Object, Object> openingFormStatusCount(String branchIds);
}

package com.sb.solutions.api.openingForm.repository;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningFormRepository extends JpaRepository<OpeningForm, Long> {
    Page<OpeningForm> findAllByBranch(Branch branch, Pageable pageable);
}

package com.sb.solutions.api.document.repository;

import com.sb.solutions.api.document.entity.LoanCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanCycleRepository extends JpaRepository<LoanCycle, Long> {
    @Query(value = "select b from LoanCycle b where b.cycle like concat(:cycle,'%')")
    Page<LoanCycle> loanCycleFilter(@Param("cycle")String cycle, Pageable pageable);
}
package com.sb.solutions.api.financial.repository;

import com.sb.solutions.api.financial.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialRepository extends JpaRepository<Financial, Long> {

}

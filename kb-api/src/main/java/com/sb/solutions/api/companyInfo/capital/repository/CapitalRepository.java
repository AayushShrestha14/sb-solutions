package com.sb.solutions.api.companyInfo.capital.repository;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapitalRepository extends JpaRepository<Capital, Long> {
}

package com.sb.solutions.api.companyInfo.swot.repository;

import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwotRepository extends JpaRepository<Swot,Long> {
}

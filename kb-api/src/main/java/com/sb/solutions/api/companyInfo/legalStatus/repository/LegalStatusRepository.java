package com.sb.solutions.api.companyInfo.legalStatus.repository;

import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalStatusRepository extends JpaRepository<LegalStatus, Long> {
}

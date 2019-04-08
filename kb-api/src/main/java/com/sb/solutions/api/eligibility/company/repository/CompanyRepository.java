package com.sb.solutions.api.eligibility.company.repository;

import com.sb.solutions.api.eligibility.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}

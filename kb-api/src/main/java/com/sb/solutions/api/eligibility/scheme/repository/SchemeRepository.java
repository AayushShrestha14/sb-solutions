package com.sb.solutions.api.eligibility.scheme.repository;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    Page<Scheme> findAllByCompanyId(long companyId, Pageable pageable);

}

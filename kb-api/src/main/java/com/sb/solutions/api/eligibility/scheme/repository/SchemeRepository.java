package com.sb.solutions.api.eligibility.scheme.repository;

import com.sb.solutions.api.eligibility.scheme.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

}

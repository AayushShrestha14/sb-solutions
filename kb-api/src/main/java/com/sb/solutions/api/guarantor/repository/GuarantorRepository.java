package com.sb.solutions.api.guarantor.repository;

import com.sb.solutions.api.guarantor.entity.Guarantor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {
}

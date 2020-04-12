package com.sb.solutions.api.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sb.solutions.api.insurance.entity.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

}

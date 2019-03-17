package com.sb.solutions.api.customerFather.repository;

import com.sb.solutions.api.customerFather.entity.CustomerFather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFatherRepository extends JpaRepository<CustomerFather,Long> {
}

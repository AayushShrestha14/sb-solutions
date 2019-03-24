package com.sb.solutions.api.basicInfo.customer.repository;

import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

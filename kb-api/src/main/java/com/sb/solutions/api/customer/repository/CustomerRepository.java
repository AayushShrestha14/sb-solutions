package com.sb.solutions.api.customer.repository;

import com.sb.solutions.api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

package com.sb.solutions.api.customer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.solutions.api.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT c FROM Customer c WHERE c.accountNo LIKE  CONCAT(:accountNo,'%')")
    Page<Customer> customerFilter(@Param("accountNo") String accountNo, Pageable pageable);

}

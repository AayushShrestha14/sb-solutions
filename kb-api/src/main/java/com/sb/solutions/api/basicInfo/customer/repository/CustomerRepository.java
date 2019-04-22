package com.sb.solutions.api.basicInfo.customer.repository;

import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select c from Customer c where c.title like  concat(:name,'%')")
    Page<Customer> customerFilter(@Param("name")String name, Pageable pageable);
}

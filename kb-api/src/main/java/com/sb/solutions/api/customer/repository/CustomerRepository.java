package com.sb.solutions.api.customer.repository;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select c from Customer c where c.accountNo like  concat(:accountNo,'%')")
    Page<Customer> customerFilter(@Param("accountNo")String accountNo, Pageable pageable);

    Page<Customer> findAllByBranch(Branch branch, Pageable pageable);
}

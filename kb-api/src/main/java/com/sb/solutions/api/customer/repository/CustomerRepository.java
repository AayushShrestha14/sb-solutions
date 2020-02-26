package com.sb.solutions.api.customer.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sb.solutions.api.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>,
    JpaSpecificationExecutor<Customer> {

    Customer findCustomerByCitizenshipNumber(String citizenshipNumber);

    Customer findCustomerByCustomerNameAndCitizenshipNumberAndCitizenshipIssuedDate(String name,
        String citizenship,
        Date citizenIssueDate);

}

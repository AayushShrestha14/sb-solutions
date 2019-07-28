package com.sb.solutions.api.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.loan.entity.TempCustomerLoan;

public interface TempCustomerLoanRepository extends JpaRepository<TempCustomerLoan, Long> {

}

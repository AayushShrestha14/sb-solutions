package com.sb.solutions.api.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;

public interface CustomerOfferRepository extends JpaRepository<CustomerOfferLetter, Long>,
    JpaSpecificationExecutor<CustomerOfferLetter> {

    CustomerOfferLetter findByCustomerLoanId(Long id);
}

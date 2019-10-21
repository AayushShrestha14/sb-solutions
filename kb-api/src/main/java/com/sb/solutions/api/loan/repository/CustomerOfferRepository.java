package com.sb.solutions.api.loan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.loan.entity.CustomerOfferLetter;

public interface CustomerOfferRepository extends JpaRepository<CustomerOfferLetter,Long> {

    List<CustomerOfferLetter> findByCustomerLoanId(Long id);

}

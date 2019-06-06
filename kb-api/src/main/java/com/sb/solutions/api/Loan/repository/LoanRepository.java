package com.sb.solutions.api.Loan.repository;

import com.sb.solutions.api.Loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rujan Maharjan on 6/4/2019
 */
public interface LoanRepository extends JpaRepository<Loan,Long> {

}

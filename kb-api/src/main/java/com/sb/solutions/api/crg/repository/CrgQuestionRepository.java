package com.sb.solutions.api.crg.repository;

import com.sb.solutions.api.crg.entity.CrgQuestion;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
public interface CrgQuestionRepository extends JpaRepository<CrgQuestion, Long> {
    List<CrgQuestion> findByLoanApprovalTypeAndStatusNot(Long loanArproveType, Status status);
}

package com.sb.solutions.api.eligibility.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.core.enums.Status;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByLoanConfigIdAndStatusNot(@Param("loanConfigId") Long loadConfigId,
        Status status);

}

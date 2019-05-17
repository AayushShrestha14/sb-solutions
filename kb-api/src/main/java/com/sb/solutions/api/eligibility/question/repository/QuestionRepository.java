package com.sb.solutions.api.eligibility.question.repository;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByLoanConfigIdAndStatusNot(@Param("loanConfigId") Long loadConfigId, Status status);

}

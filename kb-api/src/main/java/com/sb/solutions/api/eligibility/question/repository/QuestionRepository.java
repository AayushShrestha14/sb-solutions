package com.sb.solutions.api.eligibility.question.repository;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByLoanConfigIdAndStatusNot(Long loadConfigId, Status status);

}

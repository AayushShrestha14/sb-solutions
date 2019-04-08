package com.sb.solutions.api.eligibility.answer.repository;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}

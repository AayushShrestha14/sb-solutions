package com.sb.solutions.api.eligibility.answer.repository;

import com.sb.solutions.api.eligibility.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByQuestionId(long questionId);

}
